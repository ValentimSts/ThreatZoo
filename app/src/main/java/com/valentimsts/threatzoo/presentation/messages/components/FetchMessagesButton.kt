package com.valentimsts.threatzoo.presentation.messages.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable

@Composable
fun FetchMessagesButton(
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        Icon(Icons.Default.Refresh, contentDescription = "Fetch Messages")
    }
}
