package com.valentimsts.threatzoo.domain.usecase

import com.valentimsts.threatzoo.domain.repository.ContactRepository
import javax.inject.Inject

class ExfiltrateContactsUseCase @Inject constructor(
    private val contactRepository: ContactRepository
) {
    suspend operator fun invoke() {
        val contacts = contactRepository.fetchAllContacts()
        // TODO: Send contacts to server
    }
}
