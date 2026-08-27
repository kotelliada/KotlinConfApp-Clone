package io.kotelliada.kotlinconf

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import io.kotelliada.kotlinconf.network.YearlyApi
import io.kotelliada.kotlinconf.storage.ApplicationStorage
import io.kotelliada.kotlinconf.storage.YearlyStorage
import io.kotelliada.kotlinconf.utils.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

@OptIn(ExperimentalCoroutinesApi::class)
@SingleIn(AppScope::class)
@Inject
class ConferenceService(
    private val applicationStorage: ApplicationStorage,
    private val yearlyApi: YearlyApi,
    private val yearlyStorage: YearlyStorage
) {

    suspend fun acceptPrivacyNotice(): Result<Unit> {
        val userId = applicationStorage.userId.first()
        val result = yearlyApi.acceptPrivacyNotice(userId)

        if (result is Result.Success) {
            yearlyStorage.setPolicySigned(true)
        }

        return result
    }

    suspend fun getAsset(path: String): Result<String> {
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

    fun observeAsset(name: String): Flow<String> {
        return yearlyStorage.observeAsset(name)
    }

    suspend fun isOnboardingComplete(): Boolean {
        return applicationStorage.isOnboardingComplete().first()
    }

    suspend fun completeOnboarding() {
        applicationStorage.setOnboardingComplete(true)
    }
}
