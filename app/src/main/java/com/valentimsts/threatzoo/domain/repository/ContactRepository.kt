package com.valentimsts.threatzoo.domain.repository

import com.valentimsts.threatzoo.domain.model.Contact

interface ContactRepository {
    suspend fun fetchAllContacts(): List<Contact>
}