package de.netalic.peacock.ui.registeration.codeverification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import de.netalic.peacock.data.model.MyResponse
import de.netalic.peacock.data.repository.UserRepository

class CodeVerificationViewModel(private val userRepository:UserRepository) {


    private val mBindResponseLiveData= MutableLiveData<MyResponse<Long>>()

    fun getBindLiveData() :LiveData<MyResponse<Long>> {

        return mBindResponseLiveData
    }

}