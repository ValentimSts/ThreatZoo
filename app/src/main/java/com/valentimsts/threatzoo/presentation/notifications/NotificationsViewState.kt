package com.valentimsts.threatzoo.presentation.notifications

import com.valentimsts.threatzoo.domain.model.Notification
import com.valentimsts.threatzoo.presentation.notifications.utils.NotificationsFilterState

data class NotificationsViewState(
    /**
     * Whether OTPs are being fetched/loading
     */
    val isLoading: Boolean = false,

    /**
     * The complete list of all fetched OTPs
     */
    val notifications: List<Notification> = emptyList(),

    /**
     * The list of notifications that should be displayed on the screen
     */
    val visibleNotifications: List<Notification> = emptyList(),


    /**
     * The current search query text from the search bar
     */
    val searchQuery: String = "",

    /**
     * The filter state (sorting + other filters)
     */
    val filterState: NotificationsFilterState = NotificationsFilterState(),

    /**
     * An optional error message if something goes wrong
     */
    val error: String? = null
)