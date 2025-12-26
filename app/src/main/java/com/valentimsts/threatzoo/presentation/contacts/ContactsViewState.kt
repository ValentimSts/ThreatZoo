package com.valentimsts.threatzoo.presentation.contacts

import com.valentimsts.threatzoo.domain.model.Contact
import com.valentimsts.threatzoo.presentation.contacts.utils.ContactsFilterState

data class ContactsViewState(
    /**
     * Whether messages are being fetched/loading
     */
    val isLoading: Boolean = false,

    /**
     * The complete list of all fetched messages (SMS + MMS)
     */
    val allContacts: List<Contact> = emptyList(),

    /**
     * The list of visible messages in the UI after filtering/searching/sorting
     */
    val visibleContacts: List<Contact> = emptyList(),

    /**
     * The current search query text from the search bar
     */
    val searchQuery: String = "",

    /**
     * The filter state (sorting + other filters)
     */
    val filterState: ContactsFilterState = ContactsFilterState(),

    /**
     * An optional error message if something goes wrong
     */
    val error: String? = null
)