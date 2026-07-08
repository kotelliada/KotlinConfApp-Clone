package io.kotelliada.kotlinconf.home

import com.arkivanov.decompose.ComponentContext
import io.kotelliada.kotlinconf.di.AppGraph
import kotlinx.coroutines.launch

class DefaultHomeComponent(
    componentContext: ComponentContext,
    launchOnboarding: () -> Unit,
    private val appGraph: AppGraph,
) : HomeComponent, ComponentContext by componentContext {

    private val logger = appGraph.globalLogger.withTag("HomeComponent")

    init {
        appGraph.appCoroutineScope.launch(appGraph.mainDispatcher.immediate) {
            val isOnboardingComplete = appGraph.conferenceService.isOnboardingComplete()
            logger.d { "Is onboarding complete: $isOnboardingComplete" }
            if (!isOnboardingComplete) {
                launchOnboarding()
            }
        }
    }
}
