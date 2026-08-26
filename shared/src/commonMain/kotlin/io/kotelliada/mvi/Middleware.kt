package io.kotelliada.mvi

import kotlinx.coroutines.flow.Flow

interface Middleware<Action, State, Effect> {
    fun bind(
        actions: Flow<Action>,
        state: Flow<State>,
        emitEffect: (Effect) -> Unit
    ): Flow<Action>
}
