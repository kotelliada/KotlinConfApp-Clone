package io.kotelliada.kotlinconf.privacynotice.details

import co.touchlab.kermit.Logger
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.mikepenz.markdown.model.State
import io.kotelliada.kotlinconf.ConferenceService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.MainCoroutineDispatcher
import kotlinx.coroutines.flow.StateFlow

class DefaultPrivacyNoticeDetailsComponent(
    componentContext: ComponentContext,
    mainDispatcher: MainCoroutineDispatcher,
    defaultDispatcher: CoroutineDispatcher,
    globalLogger: Logger,
    private val conferenceService: ConferenceService,
    private val onBackButton: () -> Unit
) : ComponentContext by componentContext, PrivacyNoticeDetailsComponent {

    private val logger = globalLogger.withTag("PrivacyNoticeDetailsComponent")

    private val viewModel = instanceKeeper.getOrCreate {
        PrivacyNoticeDetailsViewModel(
            conferenceService = conferenceService,
            mainDispatcher = mainDispatcher,
            defaultDispatcher = defaultDispatcher,
            logger = logger
        )
    }

    override val uiState: StateFlow<State>
        get() {
            return viewModel.uiState
        }

    override fun onBackButtonClicked() {
        onBackButton()
    }
}
