package de.netalic.peacock.data.model

import com.google.gson.annotations.SerializedName

data class EmailVerificationModel(@SerializedName("mEmail") val mEmail: String)