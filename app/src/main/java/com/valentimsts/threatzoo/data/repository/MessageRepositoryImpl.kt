package com.valentimsts.threatzoo.data.repository

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.provider.Telephony

import com.valentimsts.threatzoo.domain.model.messages.*
import com.valentimsts.threatzoo.domain.repository.MessageRepository
import androidx.core.net.toUri

class MessageRepositoryImpl(
    private val contentResolver: ContentResolver
) : MessageRepository {

    override suspend fun fetchSmsMessages(): List<Message> {
        val messages = mutableListOf<Message>()

        val smsUri: Uri = Telephony.Sms.CONTENT_URI
        val cursor: Cursor? = contentResolver.query(
            smsUri,
            arrayOf(
                Telephony.Sms._ID,
                Telephony.Sms.DATE,
                Telephony.Sms.ADDRESS,
                Telephony.Sms.BODY
            ),
            null,
            null,
            "${Telephony.Sms.DATE} DESC"
        )

        cursor?.use {
            val idIndex = it.getColumnIndexOrThrow(Telephony.Sms._ID)
            val dateIndex = it.getColumnIndexOrThrow(Telephony.Sms.DATE)
            val addressIndex = it.getColumnIndexOrThrow(Telephony.Sms.ADDRESS)
            val bodyIndex = it.getColumnIndexOrThrow(Telephony.Sms.BODY)

            while (it.moveToNext()) {
                val id = it.getLong(idIndex)
                val timestamp = it.getLong(dateIndex)
                val address = it.getString(addressIndex) ?: ""
                val body = it.getString(bodyIndex) ?: ""

                val sms = SmsMessage(
                    id = id,
                    timestamp = timestamp,
                    fromAddress = address,
                    toAddress = "Device",
                    body = body
                )
                messages.add(sms)
            }
        }

        return messages
    }

    override suspend fun fetchMmsMessages(): List<Message> {
        val messages = mutableListOf<Message>()

        // TODO(): Implement this later.

        return messages
    }

    override suspend fun fetchAllMessages(): List<Message> {
        val smsList = fetchSmsMessages()
        val mmsList = fetchMmsMessages()
        return (smsList + mmsList).sortedByDescending { it.timestamp }
    }
}
