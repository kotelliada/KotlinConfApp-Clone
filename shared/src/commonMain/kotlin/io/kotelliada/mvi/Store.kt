package io.kotelliada.mvi

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.MainCoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class Store<Action, State, Effect>(
    private val reducer: Reducer<State, Action>,
    private val middlewares: List<Middleware<Action, State, Effect>>,
    private val coroutineScope: CoroutineScope,
    private val mainDispatcher: MainCoroutineDispatcher,
    initialState: State
) {
    private val _state = MutableStateFlow(initialState)

    private val actions = MutableSharedFlow<Action>(extraBufferCapacity = 64)

    private val _effects = Channel<Effect>(capacity = Channel.BUFFERED)

    val state: StateFlow<State> = _state.asStateFlow()

    val effects: Flow<Effect> = _effects.receiveAsFlow()

    init {
        coroutineScope.launch(start = CoroutineStart.UNDISPATCHED) {
            launch(start = CoroutineStart.UNDISPATCHED) {
                actions.collect { action ->
                    _state.update { currentState ->
                        reducer.reduce(currentState, action)
                    }
                }
            }

            launch(start = CoroutineStart.UNDISPATCHED) {
                middlewares.map { it.bind(actions, _state, ::emitEffect) }
                    .merge()
                    .collect { action ->
                        actions.emit(action)
                    }
            }
        }
    }

    fun acceptAction(action: Action) {
        coroutineScope.launch(mainDispatcher) {
            actions.emit(action)
        }
    }

    private fun emitEffect(effect: Effect) {
        coroutineScope.launch(mainDispatcher) {
            _effects.send(effect)
        }
    }
}
