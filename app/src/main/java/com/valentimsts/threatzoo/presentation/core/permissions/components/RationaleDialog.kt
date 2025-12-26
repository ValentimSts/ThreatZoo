package com.valentimsts.threatzoo.presentation.core.permissions.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

import com.valentimsts.threatzoo.presentation.core.permissions.RationaleTextProvider

@Composable
fun RationaleDialog(
    isPermanentlyDeclined: Boolean,
    rationaleTextProvider: RationaleTextProvider,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = rationaleTextProvider.getTitle()) },
        text = { Text(text = rationaleTextProvider.getDescription(
            isPermanentlyDeclined = isPermanentlyDeclined
        )) },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}