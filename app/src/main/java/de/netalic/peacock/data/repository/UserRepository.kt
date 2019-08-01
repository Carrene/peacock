package de.netalic.peacock.data.repository

import de.netalic.peacock.data.model.UserModel
import de.netalic.peacock.data.webservice.ApiInterface
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Response

class UserRepository(private val apiInterface: ApiInterface) : BaseRepository() {


    fun claim(phone: String, udid: String): Single<Response<UserModel>> {
        return apiInterface.claim(phone, udid)
    }

    fun bind(user:UserModel) :Single<Response<ResponseBody>>{

        return apiInterface.bind(user.mPhone,user.mUdid, user.mName!!,user.mDeviceType,user.mFirebaseToken,
            user.mActivateToken)
    }
}