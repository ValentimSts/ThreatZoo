package com.valentimsts.threatzoo.presentation.messages

import com.valentimsts.threatzoo.domain.model.messages.Message
import com.valentimsts.threatzoo.presentation.messages.utils.MessagesFilterState

data class MessagesViewState(
    /**
     * Whether messages are being fetched/loading
     */
    val isLoading: Boolean = false,

    /**
     * The complete list of all fetched messages (SMS + MMS)
     */
    val allMessages: List<Message> = emptyList(),

    /**
     * The list of visible messages in the UI after filtering/searching/sorting
     */
    val visibleMessages: List<Message> = emptyList(),

    /**
     * The current search query text from the search bar
     */
    val searchQuery: String = "",

    /**
     * The filter state (sorting + other filters)
     */
    val filterState: MessagesFilterState = MessagesFilterState(),

    /**
     * An optional error message if something goes wrong
     */
    val error: String? = null
)