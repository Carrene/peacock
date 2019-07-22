package de.netalic.peacock.data.webservice

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {

    companion object{

        fun getClient():InterfaceApi{

            val retrofit=Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://nightly-alpha.carrene.com/apiv1/")
                .build()
            return retrofit.create(InterfaceApi::class.java)

        }

    }

}