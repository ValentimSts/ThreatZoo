package com.valentimsts.threatzoo.domain.model.messages

sealed class MmsPart {
    data class Text(val text: String) : MmsPart()
    data class Image(val uri: String) : MmsPart()
    data class Audio(val uri: String) : MmsPart()
}