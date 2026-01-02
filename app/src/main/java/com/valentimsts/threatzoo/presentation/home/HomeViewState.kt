package com.valentimsts.threatzoo.presentation.home

data class HomeViewState(
    /**
     * Whether messages are being fetched/loading
     */
    val isLoading: Boolean = false,

    /**
     * An optional error message if something goes wrong
     */
    val error: String? = null
)