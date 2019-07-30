package de.netalic.peacock.ui.login.password

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import de.netalic.peacock.common.Validator
import de.netalic.peacock.data.model.MyResponse
import de.netalic.peacock.ui.base.BaseViewModel

enum class ResponseStatus {
    SUCCESS_MINIMUM_CHARS,
    SUCCESS_UPPERCASE,
    SUCCESS_DIGIT,
    SUCCESS_SPECIAL_CHAR,
    PASSWORD_MATCH,
    PASSWORD_NOT_MATCH
}

class PasswordLoginViewModel(private val validator: Validator) : BaseViewModel(){

    private var mPassword: String? = null
    private var mPasswordRepeat: String? = null

    private val mPasswordResponse = MutableLiveData<MyResponse<ResponseStatus>>()
    private val mRepeatPasswordResponse = MutableLiveData<MyResponse<ResponseStatus>>()
    private val mResponseEquality = MutableLiveData<MyResponse<ResponseStatus>>()



    fun getResponse(): LiveData<MyResponse<ResponseStatus>> {
        return mPasswordResponse
    }

    fun getRepeatResponse(): LiveData<MyResponse<ResponseStatus>> {
        return mRepeatPasswordResponse
    }

    fun getEqualityResponse(): LiveData<MyResponse<ResponseStatus>> {
        return mResponseEquality
    }

    fun onPasswordEntered(password: String) {

        var counter = 0

        if (validator.hasMinimumLength(password, 8)) {
            mPasswordResponse.value = MyResponse.success(ResponseStatus.SUCCESS_MINIMUM_CHARS)
            ++counter
        } else {
            mPasswordResponse.value = MyResponse.failed(Throwable(FAILED_MINIMUM_CHARS))
        }

        if (validator.hasCapitalLetter(password)) {
            mPasswordResponse.value = MyResponse.success(ResponseStatus.SUCCESS_UPPERCASE)
            ++counter
        } else {
            mPasswordResponse.value = MyResponse.failed(Throwable(FAILED_UPPERCASE))
        }

        if (validator.hasDigit(password)) {
            mPasswordResponse.value = MyResponse.success(ResponseStatus.SUCCESS_DIGIT)
            ++counter
        } else {
            mPasswordResponse.value = MyResponse.failed(Throwable(FAILED_DIGIT))
        }

        if (validator.hasSpecialCharacters(password)) {
            mPasswordResponse.value = MyResponse.success(ResponseStatus.SUCCESS_SPECIAL_CHAR)
            ++counter
        } else {
            mPasswordResponse.value = MyResponse.failed(Throwable(FAILED_SPECIAL_CHAR))
        }

        if (counter == 4) {
            mPassword = password
        } else {
            mPassword = null
        }

        isPasswordMatch()
    }

    fun onPasswordRepeated(password: String) {
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

    companion object {
        const val FAILED_MINIMUM_CHARS = "failed_minimum_chars"
        const val FAILED_UPPERCASE = "failed_uppercase"
        const val FAILED_DIGIT = "failed_digit"
        const val FAILED_SPECIAL_CHAR = "failed_special_char"
    }

}