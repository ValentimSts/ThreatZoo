package com.valentimsts.threatzoo.presentation.contacts

import android.Manifest
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.valentimsts.threatzoo.presentation.core.ScreenContainer
import com.valentimsts.threatzoo.presentation.core.SearchBar
import com.valentimsts.threatzoo.presentation.core.permissions.rememberSinglePermissionHandler
import com.valentimsts.threatzoo.presentation.contacts.components.FetchContactsButton
import com.valentimsts.threatzoo.presentation.contacts.components.FilterButton
import com.valentimsts.threatzoo.presentation.contacts.components.ContactCard
import com.valentimsts.threatzoo.presentation.contacts.components.ExfiltrateContactsButton
import com.valentimsts.threatzoo.presentation.messages.permissions.ReadSmsRationaleTextProvider

@Composable
fun ContactsScreen(
    viewModel: ContactsViewModel = hiltViewModel()
) {
    val viewState by viewModel.viewState

    val permissionHandler = rememberSinglePermissionHandler(
        permission = Manifest.permission.READ_CONTACTS,
        rationaleTextProvider = ReadSmsRationaleTextProvider()
    ) { isGranted ->
        if (isGranted) {
            viewModel.fetchContacts()
        }
    }

    ScreenContainer(
        title = "Contacts",
        subtitle = "View your contacts",
        permissionHandler = permissionHandler,
    ) {
        viewModel.fetchContacts()

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
                FetchContactsButton(
                    onClick = viewModel::fetchContacts
                )
                ExfiltrateContactsButton(
                    onClick = { /** TODO(): Implement exfiltrate contacts **/  }
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
                    items(viewState.visibleContacts, key = { it.id }) { contact ->
                        ContactCard(contact)
                    }
                }
            }
        }
    }
}
