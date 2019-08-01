package de.netalic.peacock.data.model

import com.google.gson.annotations.SerializedName

data class UserModel(
    @SerializedName("id") val mId: String? = null,
    @SerializedName("name") val mName: String? = null,
    @SerializedName("phone") val mPhone: String,
    @SerializedName("udid") val mUdid: String,
    @SerializedName("firebaseToken") val mFirebaseToken: String = "",
    val mActivateToken:String,
    val mDeviceType:String
)