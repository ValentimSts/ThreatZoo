package com.valentimsts.threatzoo.presentation.messages.utils

import com.valentimsts.threatzoo.presentation.core.utils.TimestampOrder

/**
 * Filters that can be applied to a list of messages.
 *
 * @param timestampOrder How messages should be ordered by timestamp
 * @param fromAddress Optional filter; if non-null, only include messages where the
 * fromAddress contains this string (case-insensitive)
 * @param toAddress Optional filter; if non-null, only include messages where the
 *  toAddress contains this string (case-insensitive)
 */
data class MessagesFilterState(
    val timestampOrder: TimestampOrder = TimestampOrder.DESCENDING,
    val fromAddress: String? = null,
    val toAddress: String? = null
)
