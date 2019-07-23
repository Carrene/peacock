package de.netalic.peacock.data.model

data class User( val phone:String,
            val udid :String,
            val deviceName:String,
            val deviceType:String,
            val firebaseRegistrationId:String,
            val actionCode:Int
)