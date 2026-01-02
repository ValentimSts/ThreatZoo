package com.valentimsts.threatzoo.presentation.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

import com.valentimsts.threatzoo.presentation.core.permissions.PermissionScreenWrapper
import com.valentimsts.threatzoo.presentation.core.permissions.RequiredPermissions.AccessAccessibilityFeatures

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val viewState by viewModel.viewState
    val focusManager = LocalFocusManager.current

    PermissionScreenWrapper(
        modifier = Modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null, // Remove the ripple effect on tap
            onClick = { focusManager.clearFocus() }
        ),
        title = "Home Screen",
        subtitle = "",
        permissions = listOf(AccessAccessibilityFeatures),
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                "Welcome"
            )
        }
    }
}
