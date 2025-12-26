package com.valentimsts.threatzoo.presentation.messages

import android.Manifest
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

import com.valentimsts.threatzoo.presentation.core.ScreenContainer
import com.valentimsts.threatzoo.presentation.messages.components.FetchMessagesButton
import com.valentimsts.threatzoo.presentation.messages.components.FilterButton
import com.valentimsts.threatzoo.presentation.messages.components.MessageCard
import com.valentimsts.threatzoo.presentation.core.SearchBar
import com.valentimsts.threatzoo.presentation.core.permissions.rememberSinglePermissionHandler
import com.valentimsts.threatzoo.presentation.messages.permissions.ReadSmsRationaleTextProvider


@Composable
fun MessagesScreen(
    viewModel: MessagesViewModel = hiltViewModel()
) {
    val viewState by viewModel.viewState

    val permissionHandler = rememberSinglePermissionHandler(
        permission = Manifest.permission.READ_SMS,
        rationaleTextProvider = ReadSmsRationaleTextProvider()
    ) { isGranted ->
        if (isGranted) {
            viewModel.fetchMessages()
        }
    }

    ScreenContainer(
        title = "Messages",
        subtitle = "View your SMS messages",
        permissionHandler = permissionHandler,
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
                    contentPadding = PaddingValues(8.dp)
                ) {
                    items(viewState.visibleMessages, key = { it.id }) { message ->
                        MessageCard(message)
                    }
                }
            }
        }
    }
}
