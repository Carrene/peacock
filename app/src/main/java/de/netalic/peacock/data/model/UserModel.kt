package de.netalic.peacock.data.model

import com.google.gson.annotations.SerializedName

data class UserModel(
    @SerializedName("id") val mId: String? = "0",
    @SerializedName("name")val mName: String? = "",
    @SerializedName("phone")val mPhone: String,
    @SerializedName("udid")val mUdid: String,
    @SerializedName("firebaseToken")val mFirebaseToken: String = ""
)