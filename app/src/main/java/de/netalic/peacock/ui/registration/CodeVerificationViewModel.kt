package de.netalic.peacock.ui.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import de.netalic.peacock.data.exception.ActivationCodeIsNotValid
import de.netalic.peacock.data.exception.BadRequestException
import de.netalic.peacock.data.exception.InvalidDeviceName
import de.netalic.peacock.data.exception.InvalidUdidOrPhone
import de.netalic.peacock.data.model.MyResponse
import de.netalic.peacock.data.model.UserModel
import de.netalic.peacock.data.repository.UserRepository
import de.netalic.peacock.ui.base.BaseViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import java.util.concurrent.TimeUnit


class CodeVerificationViewModel(private val userRepository: UserRepository) : BaseViewModel() {


    companion object {

        const val sResend = "RESEND"
    }

    private val mBindResponseLiveData = MutableLiveData<MyResponse<ResponseBody>>()
    private val mTimerLiveData = MutableLiveData<MyResponse<String>>()

    fun getTimerLiveData(): LiveData<MyResponse<String>> {

        return mTimerLiveData
    }

    fun getBindLiveData(): LiveData<MyResponse<ResponseBody>> {

        return mBindResponseLiveData
    }

    fun setTimer(time: Long = 30) {

        val timerDisposable = Observable.interval(1, TimeUnit.SECONDS)
            .take(time)
            .map { time - it }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {

                    val minuteTimer = TimeUnit.SECONDS.toMinutes(it)
                    val secondTimer = TimeUnit.SECONDS.toSeconds(it) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(it))
                    mTimerLiveData.value = MyResponse.success(String.format("%02d:%02d ", minuteTimer, secondTimer))

                },
                {

                },
                {
                    mTimerLiveData.value = MyResponse.success(sResend)

                }
            )

        mCompositeDisposable.add(timerDisposable)

    }

    fun bind(user: UserModel) {

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
                        mBindResponseLiveData.value = MyResponse.failed(BadRequestException())
                    }
                    710 -> {
                        mBindResponseLiveData.value = MyResponse.failed(InvalidUdidOrPhone())
                    }
                    711 -> {
                        mBindResponseLiveData.value = MyResponse.failed(ActivationCodeIsNotValid())
                    }
                    716 -> {
                        mBindResponseLiveData.value = MyResponse.failed(InvalidDeviceName())
                    }

                }
            },
                {

                })

        mCompositeDisposable.add(disposable)
    }
}