package com.valentimsts.threatzoo.presentation.core.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ScreenTitleBar(
    title: String,
    subtitle: String
) {
    Column(
        modifier = Modifier.padding(bottom = 10.dp)
    ) {
        Text(title, style = MaterialTheme.typography.titleMedium)
        Text(subtitle, style = MaterialTheme.typography.labelSmall)
    }
}