package io.kotelliada.kotlinconf

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import io.kotelliada.kotlinconf.network.YearlyApi
import io.kotelliada.kotlinconf.storage.ApplicationStorage
import io.kotelliada.kotlinconf.storage.YearlyStorage
import io.kotelliada.kotlinconf.utils.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first

@OptIn(ExperimentalCoroutinesApi::class)
@SingleIn(AppScope::class)
@Inject
class ConferenceService(
    private val appInitializer: AppInitializer,
    private val applicationStorage: ApplicationStorage,
    private val yearlyApi: YearlyApi,
    private val yearlyStorage: YearlyStorage
) {

    suspend fun acceptPrivacyNotice(): Result<Unit> {
        awaitAppInitialization()

        val userId = applicationStorage.userId.first()
        val result = yearlyApi.acceptPrivacyNotice(userId)

        if (result is Result.Success) {
            yearlyStorage.setPolicySigned(true)
        }

        return result
    }

    suspend fun getAsset(path: String): Result<String> {
        awaitAppInitialization()

        val cachedAsset = yearlyStorage.getAsset(path)
        if (cachedAsset != null) {
            return Result.Success(cachedAsset)
        }

        val userId = applicationStorage.userId.first()
        val downloadedAsset = yearlyApi.downloadAsset(userId, path)

        if (downloadedAsset is Result.Success) {
            yearlyStorage.setAsset(path, downloadedAsset.value)
        }

        return downloadedAsset
    }

    suspend fun isOnboardingComplete(): Boolean {
        awaitAppInitialization()
        return applicationStorage.isOnboardingComplete().first()
    }

    suspend fun completeOnboarding() {
        awaitAppInitialization()
        applicationStorage.setOnboardingComplete(true)
    }

    private suspend fun awaitAppInitialization() {
        appInitializer.isAppInitializedFlow
            .first { it }
    }
}
