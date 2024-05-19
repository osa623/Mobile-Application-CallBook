package com.example.callbook.contactListDatabaseConfigs

data class contacts(


    val id: Int,
    val image: ByteArray?,
    val firstName: String,
    val lastName: String,
    val contactNumber : Int,
    val email : String,
    val notes :String
)
