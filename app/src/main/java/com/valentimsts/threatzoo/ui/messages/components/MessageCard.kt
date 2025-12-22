package com.valentimsts.threatzoo.ui.messages.components

import androidx.compose.runtime.Composable

import com.valentimsts.threatzoo.domain.model.messages.Message
import com.valentimsts.threatzoo.domain.model.messages.MmsMessage
import com.valentimsts.threatzoo.domain.model.messages.SmsMessage

@Composable
fun MessageCard(message: Message) {
    when (message) {
        is SmsMessage -> SmsMessageCard(message)
        is MmsMessage -> MmsMessageCard(message)
    }
}