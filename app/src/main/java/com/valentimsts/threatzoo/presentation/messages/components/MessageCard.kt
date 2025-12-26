package com.valentimsts.threatzoo.presentation.messages.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.valentimsts.threatzoo.domain.model.messages.Message
import com.valentimsts.threatzoo.domain.model.messages.MessageType
import com.valentimsts.threatzoo.domain.model.messages.MmsMessage
import com.valentimsts.threatzoo.domain.model.messages.SmsMessage
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun MessageCard(message: Message) {
    val cardColors = when (message.type) {
        MessageType.SENT -> CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
        else -> CardDefaults.cardColors()
    }

    val alignment = when (message.type) {
        MessageType.SENT -> Alignment.CenterEnd
        MessageType.RECEIVED -> Alignment.CenterStart
        else -> Alignment.Center
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        contentAlignment = alignment
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = cardColors
        ) {
            val dateFormatter = remember {
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            }

            Column(modifier = Modifier.padding(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "From: ${message.fromAddress}",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Text(
                        text = "To: ${message.toAddress}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Time: ${dateFormatter.format(Date(message.timestamp))}",
                    style = MaterialTheme.typography.bodySmall
                )

                Spacer(modifier = Modifier.height(4.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(4.dp))

                when (message) {
                    is SmsMessage -> SmsMessageContent(message)
                    is MmsMessage -> MmsMessageContent(message)
                }
            }
        }
    }
}