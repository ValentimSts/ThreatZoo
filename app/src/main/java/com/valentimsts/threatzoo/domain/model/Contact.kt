package com.valentimsts.threatzoo.domain.model

data class Contact(
    val id: Long,
    val name: String,
    val phoneNumbers: List<String>
)
