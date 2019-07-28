package de.netalic.peacock.ui.login.password

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.raywenderlich.android.validatetor.ValidateTor
import de.netalic.peacock.data.model.MyResponse
import de.netalic.peacock.ui.base.BaseViewModel
import org.koin.core.KoinComponent
import org.koin.core.inject

enum class ResponseStatus {
    PART_ONE_COMPLETED,
    PART_TWO_COMPLETED,
    PART_THREE_COMPLETED,
    PART_FOUR_COMPLETED,
    PART_ONE_FAILED,
    PART_TWO_FAILED,
    PART_THREE_FAILED,
    PART_FOUR_FAILED

}

class PasswordLoginViewModel : BaseViewModel(), KoinComponent {

    private val validateTor: ValidateTor by inject()

    private val mResponse = MutableLiveData<MyResponse<ResponseStatus>>()

    fun getResponse(): LiveData<MyResponse<ResponseStatus>> {
        return mResponse
    }

    fun onPasswordEntered(password: String) {

        if (validateTor.isAtleastLength(password, 8) && validateTor.isAtMostLength(password, 20)) {
            mResponse.value = MyResponse.success(ResponseStatus.PART_ONE_COMPLETED)
        } else {
            mResponse.value = MyResponse.success(ResponseStatus.PART_ONE_FAILED)
        }

        if (validateTor.hasAtleastOneUppercaseCharacter(password)) {
            mResponse.value = MyResponse.success(ResponseStatus.PART_TWO_COMPLETED)
        } else {
            mResponse.value = MyResponse.success(ResponseStatus.PART_TWO_FAILED)
        }

        if (validateTor.hasAtleastOneDigit(password)) {
            mResponse.value = MyResponse.success(ResponseStatus.PART_THREE_COMPLETED)
        } else {
            mResponse.value = MyResponse.success(ResponseStatus.PART_THREE_FAILED)
        }

        if (password.isNotBlank()) {
            mResponse.value = MyResponse.success(ResponseStatus.PART_FOUR_COMPLETED)
        } else {
            mResponse.value = MyResponse.success(ResponseStatus.PART_FOUR_FAILED)
        }

    }

    fun onPasswordRepeated() {

    }

}