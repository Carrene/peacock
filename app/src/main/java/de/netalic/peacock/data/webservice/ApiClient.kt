package de.netalic.peacock.data.webservice

import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import de.netalic.peacock.common.MyApplication
import nuesoft.helpdroid.network.SharedPreferencesJwtPersistor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class ApiClient {


    companion object {
        private var sRetrofit: Retrofit? = null
        private var sApi: ApiInterface? = null


        private fun getClient(): Retrofit {

            if (sRetrofit == null) {

                val okHttpClient = OkHttpClient().newBuilder()
                val cookieJar = PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(MyApplication.instance))
                okHttpClient.cookieJar(cookieJar).addInterceptor(AuthorizationInterceptor())

                sRetrofit = Retrofit.Builder().baseUrl("https://nightly-alpha.carrene.com/apiv1/")
                    .client(okHttpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
            }
            return sRetrofit!!
        }


        fun getService(): ApiInterface? {

            if (sApi == null) {
                sApi = getClient().create(ApiInterface::class.java)
            }
            return sApi
        }
    }

    private class AuthorizationInterceptor : Interceptor {

        internal var sharedPreferencesJwtPersistor = SharedPreferencesJwtPersistor(MyApplication.instance)

        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {

            val token = sharedPreferencesJwtPersistor.get()
            var request = chain.request()

            if (token != null) {
                request = request.newBuilder().addHeader("Authorization", "Bearer $token").build()
            }

            var response = chain.proceed(request)

            if (response.request().method() == "BIND" && response.code() == 200) {

                val responseBodyString = response.body()!!.string()
                val contentType = response.body()!!.contentType()
                val body = ResponseBody.create(contentType, responseBodyString)
                response = response.newBuilder().body(body).build()
            }

            val newJwtToken = response.header("X-New-JWT-Token")

            if (newJwtToken != null) {
                sharedPreferencesJwtPersistor.save(newJwtToken)
            }

            return response
        }
    }

}