package io.kotelliada.kotlinconf

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import io.kotelliada.kotlinconf.storage.ApplicationStorage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first

@OptIn(ExperimentalCoroutinesApi::class)
@SingleIn(AppScope::class)
@Inject
class ConferenceService(
    private val appInitializer: AppInitializer,
    private val applicationStorage: ApplicationStorage
) {

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
