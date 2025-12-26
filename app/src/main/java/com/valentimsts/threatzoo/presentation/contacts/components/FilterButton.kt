package com.valentimsts.threatzoo.presentation.contacts.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp

import com.valentimsts.threatzoo.presentation.contacts.utils.ContactsFilterState

@Composable
fun FilterButton(
    filterState: ContactsFilterState,
    onFilterStateChange: (ContactsFilterState) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val screenWidth = LocalWindowInfo.current.containerSize.width.dp

    Box {
        IconButton(onClick = { expanded = true }) {
            Icon(
                imageVector = Icons.Default.FilterList,
                contentDescription = "Filter Messages"
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.width(screenWidth - 32.dp)
        ) {
            FilterControls(
                filterState = filterState,
                onFilterStateChange = onFilterStateChange
            )
        }
    }
}
