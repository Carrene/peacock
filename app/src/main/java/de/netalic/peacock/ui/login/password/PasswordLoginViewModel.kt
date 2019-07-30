package de.netalic.peacock.ui.login.password

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.raywenderlich.android.validatetor.ValidateTor
import de.netalic.peacock.data.model.MyResponse
import de.netalic.peacock.ui.base.BaseViewModel
import org.koin.core.KoinComponent
import org.koin.core.inject
import timber.log.Timber

enum class ResponseStatus {
    PART_ONE_COMPLETED,
    PART_TWO_COMPLETED,
    PART_THREE_COMPLETED,
    PART_FOUR_COMPLETED,
    PART_ONE_FAILED,
    PART_TWO_FAILED,
    PART_THREE_FAILED,
    PART_FOUR_FAILED,
    PASSWORD_MATCH,
    PASSWORD_NOT_MATCH
}

class PasswordLoginViewModel : BaseViewModel(), KoinComponent {

    private var mPassword: String? = null
    private var mPasswordRepeat: String? = null
    private val validateTor: ValidateTor by inject()

    private val mResponse = MutableLiveData<MyResponse<ResponseStatus>>()
    private val mRepeatResponse = MutableLiveData<MyResponse<ResponseStatus>>()
    private val mResponseEquality = MutableLiveData<MyResponse<ResponseStatus>>()

    fun getResponse(): LiveData<MyResponse<ResponseStatus>> {
        return mResponse
    }

    fun getRepeatResponse(): LiveData<MyResponse<ResponseStatus>> {
        return mRepeatResponse
    }

    fun getEqualityResponse(): LiveData<MyResponse<ResponseStatus>> {
        return mResponseEquality
    }

    fun onPasswordEntered(password: String) {

        var counter = 0

        if (validateTor.isAtleastLength(password, 8) && validateTor.isAtMostLength(password, 20)) {
            mResponse.value = MyResponse.success(ResponseStatus.PART_ONE_COMPLETED)
            ++counter
        } else {
            mResponse.value = MyResponse.success(ResponseStatus.PART_ONE_FAILED)
        }

        if (validateTor.hasAtleastOneUppercaseCharacter(password)) {
            mResponse.value = MyResponse.success(ResponseStatus.PART_TWO_COMPLETED)
            ++counter
        } else {
            mResponse.value = MyResponse.success(ResponseStatus.PART_TWO_FAILED)
        }

        if (validateTor.hasAtleastOneDigit(password)) {
            mResponse.value = MyResponse.success(ResponseStatus.PART_THREE_COMPLETED)
            ++counter
        } else {
            mResponse.value = MyResponse.success(ResponseStatus.PART_THREE_FAILED)
        }

        if (password.isNotBlank()) {
            mResponse.value = MyResponse.success(ResponseStatus.PART_FOUR_COMPLETED)
            ++counter
        } else {
            mResponse.value = MyResponse.success(ResponseStatus.PART_FOUR_FAILED)
        }

        if (counter == 4) {
            mPassword = password
        } else {
            mPassword = null
        }

        isPasswordMatch()

    }

    fun onPasswordRepeated(password: String) {
        Timber.tag("Password").d("Password = $mPassword - - - Repeat Password = $password")
        mPasswordRepeat = password
        isPasswordMatch()
    }

    private fun isPasswordMatch() {
        if (mPassword == null || mPasswordRepeat == null) {
            mResponseEquality.value = MyResponse.success(ResponseStatus.PASSWORD_NOT_MATCH)
            return
        }
        if (mPassword == mPasswordRepeat) {
            mResponseEquality.value = MyResponse.success(ResponseStatus.PASSWORD_MATCH)
        } else {
            mResponseEquality.value = MyResponse.success(ResponseStatus.PASSWORD_NOT_MATCH)
        }
    }

}