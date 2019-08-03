package de.netalic.peacock.data.repository

import de.netalic.peacock.data.model.EmailVerificationModel
import de.netalic.peacock.data.webservice.ApiInterface
import io.reactivex.Single
import retrofit2.Response

class EmailRepository(private val apiInterface: ApiInterface) : BaseRepository() {

    fun setEmail(token: String, email: String): Single<Response<EmailVerificationModel>> {
        return apiInterface.setEmail(token, email)
    }
}