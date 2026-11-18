package io.kotelliada.kotlinconf.speakers.list

import io.kotelliada.kotlinconf.Speaker

data class SpeakerWithHighlights(
    val speaker: Speaker,
    val nameHighlights: List<IntRange> = emptyList(),
    val titleHighlights: List<IntRange> = emptyList(),
)
