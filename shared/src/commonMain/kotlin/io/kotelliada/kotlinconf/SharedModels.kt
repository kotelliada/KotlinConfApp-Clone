package io.kotelliada.kotlinconf

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@Serializable
class Speaker(
    val id: SpeakerId,
    val name: String,
    val position: String,
    val description: String,
    val photoUrl: String
)

@Serializable
@JvmInline
value class SpeakerId(val id: String)
