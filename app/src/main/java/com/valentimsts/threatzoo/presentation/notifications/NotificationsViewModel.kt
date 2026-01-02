package com.valentimsts.threatzoo.presentation.notifications

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valentimsts.threatzoo.domain.repository.NotificationRepository
import com.valentimsts.threatzoo.presentation.core.utils.TimestampOrder
import com.valentimsts.threatzoo.presentation.notifications.utils.NotificationsFilterState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val repository: NotificationRepository,
) : ViewModel() {
    var viewState = mutableStateOf(NotificationsViewState())

    init {
        startNotificationListener()
    }

    private fun startNotificationListener() {
        repository.getAllNotifications().onEach { notifications ->
            viewState.value = viewState.value.copy(notifications = notifications)
            applyFilters()
        }.launchIn(viewModelScope)
    }

    fun updateSearchQuery(query: String) {
        viewState.value = viewState.value.copy(searchQuery = query)
        applyFilters()
    }

    fun updateFilter(filterState: NotificationsFilterState) {
        viewState.value = viewState.value.copy(filterState = filterState)
        applyFilters()
    }

    private fun applyFilters() {
        val state = viewState.value
        val query = state.searchQuery.lowercase()

        val filtered = state.notifications
            .filter {
                (query.isEmpty()
                        || it.originApp.lowercase().contains(query)
                        || it.content.lowercase().contains(query))
            }.sortedBy { 
                if (state.filterState.timestampOrder == TimestampOrder.ASCENDING) it.timestamp
                else -it.timestamp
            }

        viewState.value = state.copy(visibleNotifications = filtered)
    }
}
