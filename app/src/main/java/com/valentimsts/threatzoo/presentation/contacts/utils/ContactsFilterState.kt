package com.valentimsts.threatzoo.presentation.contacts.utils

import com.valentimsts.threatzoo.presentation.core.utils.TimestampOrder

/**
 * Filters that can be applied to a list of messages.
 *
 * @param timestampOrder How messages should be ordered by timestamp
 */
data class ContactsFilterState(
    val timestampOrder: TimestampOrder = TimestampOrder.DESCENDING,
)
