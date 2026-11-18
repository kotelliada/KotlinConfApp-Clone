package io.kotelliada.kotlinconf.speakers

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import io.kotelliada.kotlinconf.speakers.list.SpeakersListComponent

interface SpeakersComponent {
    val childStack: Value<ChildStack<*, Child>>

    sealed class Child {
        class SpeakersList(val component: SpeakersListComponent) : Child()
        class SpeakerDetails() : Child()
    }
}
