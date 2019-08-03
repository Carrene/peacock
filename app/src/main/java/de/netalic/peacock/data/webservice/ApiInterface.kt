package de.netalic.peacock.data.webservice


import de.netalic.peacock.data.model.UserModel
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.HTTP


interface ApiInterface {

    @FormUrlEncoded
    @HTTP(method = "CLAIM", path = "login", hasBody = true)
    fun claim(@Field("phone") phone: String, @Field("udid") udid: String): Single<Response<UserModel>>


    @FormUrlEncoded
    @HTTP(method = "BIND",path ="login", hasBody = true)
    fun bind(@Field("phone") phone:String , @Field("udid") udid:String ,
             @Field("deviceName") deviceName:String,@Field("deviceType") deviceType:String ,
             @Field("firebaseRegistrationId") firebaseRegistrationId:String,
             @Field("activationCode") activationCode:String):Single<Response<ResponseBody>>

}