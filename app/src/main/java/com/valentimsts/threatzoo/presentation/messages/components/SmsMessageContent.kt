package com.valentimsts.threatzoo.presentation.messages.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import com.valentimsts.threatzoo.domain.model.messages.SmsMessage

@Composable
fun SmsMessageContent(
    message: SmsMessage,
) {
    Text(
        text = message.body,
        style = MaterialTheme.typography.bodyLarge
    )
}
