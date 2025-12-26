package com.valentimsts.threatzoo.presentation.notifications.utils

import com.valentimsts.threatzoo.presentation.core.utils.TimestampOrder

/**
 * Filters that can be applied to a list of notifications.
 *
 * @param timestampOrder How messages should be ordered by timestamp
 * @param originApp Optional filter; if non-null, only include notifications
 * where the originApp field contains this string (case-insensitive)
 */
data class NotificationsFilterState(
    val timestampOrder: TimestampOrder = TimestampOrder.DESCENDING,
    val originApp: String? = null,
)
