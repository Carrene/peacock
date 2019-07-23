package de.netalic.peacock.ui.registeration.codeverification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import de.netalic.peacock.data.model.MyResponse
import de.netalic.peacock.data.model.User
import de.netalic.peacock.data.repository.UserRepository
import de.netalic.peacock.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CodeVerificationViewModel(private val userRepository:UserRepository): BaseViewModel() {


    private val mBindResponseLiveData= MutableLiveData<MyResponse<Long>>()

    fun getBindLiveData() :LiveData<MyResponse<Long>> {

        return mBindResponseLiveData
    }

    fun bind(user:User){

        val disposable=userRepository.bind(user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

                when(it.toInt()){


                }
            },{
                mBindResponseLiveData.value = MyResponse.failed(it)
            })

        mCompositDisposable.add(disposable)
    }

}