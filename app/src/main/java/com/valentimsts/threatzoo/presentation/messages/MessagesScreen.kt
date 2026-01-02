package com.valentimsts.threatzoo.presentation.messages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

import com.valentimsts.threatzoo.presentation.messages.components.FetchMessagesButton
import com.valentimsts.threatzoo.presentation.messages.components.FilterButton
import com.valentimsts.threatzoo.presentation.messages.components.MessageCard
import com.valentimsts.threatzoo.presentation.core.components.SearchBar
import com.valentimsts.threatzoo.presentation.core.permissions.PermissionScreenWrapper
import com.valentimsts.threatzoo.presentation.core.permissions.RequiredPermissions.ReadMessages


@Composable
fun MessagesScreen(
    viewModel: MessagesViewModel = hiltViewModel()
) {
    val viewState by viewModel.viewState
    val focusManager = LocalFocusManager.current

    PermissionScreenWrapper(
        modifier = Modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null, // Remove the ripple effect on tap
            onClick = { focusManager.clearFocus() }
        ),
        title = "Messages Screen",
        subtitle = "View your messages",
        permissions = listOf(ReadMessages),
    ) {
        viewModel.fetchMessages()

        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SearchBar(
                    modifier = Modifier.weight(0.6f),
                    query = viewState.searchQuery,
                    onQueryChange = viewModel::updateSearchQuery,
                    label = "Search Messages"
                )
                FilterButton(
                    filterState = viewState.filterState,
                    onFilterStateChange = viewModel::updateFilter
                )
                FetchMessagesButton(
                    onClick = viewModel::fetchMessages
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
                    contentPadding = PaddingValues(horizontal = 8.dp)
                ) {
                    items(viewState.visibleMessages, key = { it.id }) { message ->
                        MessageCard(message)
                    }
                }
            }
        }
    }
}
