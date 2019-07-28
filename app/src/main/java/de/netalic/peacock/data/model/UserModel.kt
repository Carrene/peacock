package de.netalic.peacock.data.model

data class UserModel(
    //TODO-tina Name convention
    val id: String?,
    val name: String?,
    val phone: String,
    val udid: String,
    val firebaseToken: String
)