package de.netalic.peacock.data.repository

import de.netalic.peacock.data.model.User
import de.netalic.peacock.data.webservice.InterfaceApi
import io.reactivex.Single


class UserRepository(private val usrApi:InterfaceApi){


   fun bind(user:User) :Single<Long>{

       return usrApi.bind(user.phone,user.udid,user.deviceName,user.deviceType,user.firebaseRegistrationId,
           user.actionCode)
   }

}