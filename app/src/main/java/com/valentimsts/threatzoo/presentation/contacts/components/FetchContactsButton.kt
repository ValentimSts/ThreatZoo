package com.valentimsts.threatzoo.presentation.contacts.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable

@Composable
fun FetchContactsButton(
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        Icon(Icons.Default.Refresh, contentDescription = "Fetch Contacts")
    }
}