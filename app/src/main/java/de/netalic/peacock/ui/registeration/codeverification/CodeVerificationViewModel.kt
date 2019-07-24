package de.netalic.peacock.ui.registeration.codeverification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import de.netalic.peacock.data.model.MyResponse
import de.netalic.peacock.data.model.User
import de.netalic.peacock.data.repository.UserRepository
import de.netalic.peacock.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody

class CodeVerificationViewModel(private val userRepository: UserRepository) : BaseViewModel() {


    private val mBindResponseLiveData = MutableLiveData<MyResponse<ResponseBody>>()

    fun getBindLiveData(): LiveData<MyResponse<ResponseBody>> {

        return mBindResponseLiveData
    }

    fun bind(user: User) {

        val disposable = userRepository.bind(user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { mBindResponseLiveData.value = MyResponse.loading() }
            .subscribe({

                when (it.code()) {

                    200 -> {
                        mBindResponseLiveData.value = MyResponse.success(it.body()!!)
                    }
                    400 -> {
                        mBindResponseLiveData.value = MyResponse.success(it.body()!!)
                    }
                    700 -> {
                        mBindResponseLiveData.value
                    }


                }
            },
                {

                })

        mCompositDisposable.add(disposable)
    }

}