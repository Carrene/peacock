package de.netalic.peacock.ui.registeration.codeverification

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import de.netalic.peacock.data.exception.ActivationCodeIsNotValid
import de.netalic.peacock.data.exception.BadRequestException
import de.netalic.peacock.data.exception.InvalidDeviceName
import de.netalic.peacock.data.exception.InvalidUdidOrPhone
import de.netalic.peacock.data.model.MyResponse
import de.netalic.peacock.data.model.User
import de.netalic.peacock.data.repository.UserRepository
import de.netalic.peacock.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import java.util.concurrent.TimeUnit

class CodeVerificationViewModel(private val userRepository: UserRepository) : BaseViewModel() {


    companion object {

        var sTimer = 30000
        const val sResend="RESEND"
    }

    private lateinit var mCountDownTimer: CountDownTimer
    private val mBindResponseLiveData = MutableLiveData<MyResponse<ResponseBody>>()
    private val mTimerLiveData = MutableLiveData<MyResponse<String>>()

    fun getTimerLiveData(): LiveData<MyResponse<String>> {

        return mTimerLiveData
    }

    fun getBindLiveData(): LiveData<MyResponse<ResponseBody>> {

        return mBindResponseLiveData
    }

    fun setTimer() {


        mCountDownTimer = object : CountDownTimer(sTimer.toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {

                val minuteTimer = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
                val secondTimer = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
                )
                mTimerLiveData.value = MyResponse.success(String.format("%02d:%02d ", minuteTimer, secondTimer))
            }

            override fun onFinish() {

                mTimerLiveData.value = MyResponse.success(sResend)

            }

        }.start()
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