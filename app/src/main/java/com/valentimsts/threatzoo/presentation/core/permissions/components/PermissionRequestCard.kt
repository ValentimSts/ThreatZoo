package com.valentimsts.threatzoo.presentation.core.permissions.components

import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Architecture
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

import com.valentimsts.threatzoo.presentation.core.permissions.Permission

@Composable
fun PermissionRequestCard(
    missingPermissions: List<Permission>,
    onRequest: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Icon(
                imageVector = Icons.Default.Architecture,
                contentDescription = "",
                modifier = Modifier.size(50.dp),
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "This feature requires the following permissions to be granted:",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = missingPermissions.joinToString(", ") { it.name },
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
            )

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = onRequest) {
                Text("Grant Permissions")
            }
        }

        /**
        Card(
            modifier = Modifier.padding(24.dp)) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(text = title, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(16.dp))
                missing.forEach {
                    Text(text = "- ${it.name}")
                }
                Spacer(modifier = Modifier.height(24.dp))
                Button(onClick = onRequest) {
                    Text("Grant Permissions")
                }
            }
        }
        **/
    }
}
