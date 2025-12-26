package com.valentimsts.threatzoo.presentation.messages

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valentimsts.threatzoo.domain.repository.MessageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

import com.valentimsts.threatzoo.presentation.messages.utils.MessagesFilterState
import com.valentimsts.threatzoo.presentation.core.utils.TimestampOrder

@HiltViewModel
class MessagesViewModel @Inject constructor(
    private val repository: MessageRepository
) : ViewModel() {
    var viewState = mutableStateOf(MessagesViewState())

    fun fetchMessages() {
        viewState.value = viewState.value.copy(isLoading = true)

        viewModelScope.launch {
            val messages = repository.fetchAllMessages()
            viewState.value = viewState.value.copy(
                isLoading = false,
                allMessages = messages
            )
            applyFilters()
        }
    }

    fun updateSearchQuery(query: String) {
        viewState.value = viewState.value.copy(searchQuery = query)
        applyFilters()
    }

    fun updateFilter(filterState: MessagesFilterState) {
        viewState.value = viewState.value.copy(filterState = filterState)
        applyFilters()
    }

    private fun applyFilters() {
        val state = viewState.value
        val query = state.searchQuery.lowercase()

        val filtered = state.allMessages
            .filter { msg ->
                (query.isEmpty()
                        || msg.fromAddress.lowercase().contains(query)
                        || msg.toAddress.lowercase().contains(query)
                        || msg.timestamp.toString().contains(query)
                        )
            }.sortedBy {
                if (state.filterState.timestampOrder == TimestampOrder.ASCENDING) it.timestamp
                else -it.timestamp
            }

        viewState.value = state.copy(visibleMessages = filtered)
    }
}
