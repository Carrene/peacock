package de.netalic.peacock.data.webservice

import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.HTTP

interface InterfaceApi {

    @FormUrlEncoded
    @HTTP(method = "BIND",path ="login", hasBody = true)
    fun bind(@Field("phone") phone:String , @Field("udid") udid:String ,
             @Field("deviceName") deviceName:String,@Field("deviceType") deviceType:String ,
             @Field("firebaseRegistrationId") firebaseRegistrationId:String,
             @Field("activationCode") activationCode:Int):Single<Long>
}