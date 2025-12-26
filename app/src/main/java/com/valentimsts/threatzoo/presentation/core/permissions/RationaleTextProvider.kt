package com.valentimsts.threatzoo.presentation.core.permissions

interface RationaleTextProvider {
    fun getTitle(): String
    fun getDescription(isPermanentlyDeclined: Boolean): String
}