package com.valentimsts.threatzoo.data.repository

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.provider.ContactsContract
import com.valentimsts.threatzoo.domain.model.Contact
import com.valentimsts.threatzoo.domain.repository.ContactRepository

class ContactRepositoryImpl(
    private val contentResolver: ContentResolver
) : ContactRepository {

    @SuppressLint("Range")
    override suspend fun fetchAllContacts(): List<Contact> {
        val contacts = mutableListOf<Contact>()

        val cursor = contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            arrayOf(
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.HAS_PHONE_NUMBER
            ),
            null,
            null,
            null
        )

        cursor?.use {
            val idColumn = it.getColumnIndex(ContactsContract.Contacts._ID)
            val nameColumn = it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
            val hasPhoneColumn = it.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)

            while (it.moveToNext()) {
                if (it.getInt(hasPhoneColumn) > 0) {
                    val id = it.getLong(idColumn)

                    contacts.add(Contact(
                        id = id,
                        name = it.getString(nameColumn),
                        phoneNumbers = fetchPhoneNumbers(id))
                    )
                }
            }
        }

        return contacts
    }

    private fun fetchPhoneNumbers(contactId: Long): List<String> {
        val phoneNumbers = mutableListOf<String>()
        val cursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
            arrayOf(contactId.toString()),
            null
        )

        cursor?.use {
            val numberColumn = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
            while (it.moveToNext()) {
                phoneNumbers.add(it.getString(numberColumn))
            }
        }

        return phoneNumbers
    }
}
