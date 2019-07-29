package de.netalic.peacock.ui.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import de.netalic.peacock.data.exception.BadRequestException
import de.netalic.peacock.data.exception.InvalidUdidOrPhoneException
import de.netalic.peacock.data.exception.ServerException
import de.netalic.peacock.data.model.MyResponse
import de.netalic.peacock.data.model.UserModel
import de.netalic.peacock.data.repository.UserRepository
import de.netalic.peacock.ui.base.BaseViewModel
import de.netalic.peacock.util.ValidatorUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

enum class ResponseStatus {
    PHONE_VALID,
    PHONE_INVALID
}

class RegistrationViewModel(private val userRepository: UserRepository) : BaseViewModel() {

    private val mClaimResponseLiveData = MutableLiveData<MyResponse<UserModel>>()
    private val mPhoneValidatorLiveData = MutableLiveData<MyResponse<ResponseStatus>>()

    fun getClaimLiveData(): LiveData<MyResponse<UserModel>> {
        return mClaimResponseLiveData
    }

    fun getPhoneValidatorLiveData(): LiveData<MyResponse<ResponseStatus>> {
        return mPhoneValidatorLiveData
    }

    fun claim(phone: String, udid: String) {

        val disposable = userRepository.claim(phone, udid)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { mClaimResponseLiveData.value = MyResponse.loading() }
            .subscribe({
                when (it.code()) {
                    200 -> mClaimResponseLiveData.value = MyResponse.success(it.body()!!)
                    400 -> mClaimResponseLiveData.value = MyResponse.failed(BadRequestException())
                    500 -> mClaimResponseLiveData.value = MyResponse.failed(ServerException())
                    710 -> mClaimResponseLiveData.value =
                        MyResponse.failed(InvalidUdidOrPhoneException())
                }
            }, { throwable ->
                mClaimResponseLiveData.value = MyResponse.failed(throwable)
            })
        mCompositeDisposable.add(disposable)
    }

    fun phoneValidator(phoneNumber: String) {

        if (ValidatorUtils.phoneValidator(phoneNumber))
            mPhoneValidatorLiveData.value = MyResponse.success(ResponseStatus.PHONE_VALID)
        else
            mPhoneValidatorLiveData.value = MyResponse.success(ResponseStatus.PHONE_INVALID)

    }

}