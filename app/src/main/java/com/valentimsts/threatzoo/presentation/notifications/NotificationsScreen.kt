package com.valentimsts.threatzoo.presentation.notifications

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

import com.valentimsts.threatzoo.presentation.core.components.SearchBar
import com.valentimsts.threatzoo.presentation.core.permissions.PermissionScreenWrapper
import com.valentimsts.threatzoo.presentation.core.permissions.RequiredPermissions.AccessNotifications
import com.valentimsts.threatzoo.presentation.notifications.components.FilterButton
import com.valentimsts.threatzoo.presentation.notifications.components.NotificationCard

@Composable
fun NotificationsScreen(
    viewModel: NotificationsViewModel = hiltViewModel()
) {
    val viewState by viewModel.viewState
    val focusManager = LocalFocusManager.current

    PermissionScreenWrapper(
        modifier = Modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null, // Remove the ripple effect on tap
            onClick = { focusManager.clearFocus() }
        ),
        title = "Notifications Screen",
        subtitle = "View your notifications",
        permissions = listOf(AccessNotifications),
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SearchBar(
                    modifier = Modifier.weight(0.6f),
                    query = viewState.searchQuery,
                    onQueryChange = viewModel::updateSearchQuery,
                    label = "Search Notifications"
                )
                FilterButton(
                    filterState = viewState.filterState,
                    onFilterStateChange = viewModel::updateFilter
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(8.dp))

            if (viewState.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(8.dp)
                ) {
                    items(viewState.visibleNotifications, key = { it.id }) { notification ->
                        NotificationCard(notification)
                    }
                }
            }
        }
    }
}
