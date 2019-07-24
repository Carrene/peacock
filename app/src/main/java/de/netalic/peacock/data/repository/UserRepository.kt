package de.netalic.peacock.data.repository

import de.netalic.peacock.data.model.UserModel
import de.netalic.peacock.data.webservice.ApiInterface
import io.reactivex.Single
import retrofit2.Response

class UserRepository(private val apiInterface: ApiInterface) : BaseRepository() {


    fun claim(phone: String, udid: String): Single<Response<UserModel>> {
        return apiInterface.claim(phone, udid)
    }
}