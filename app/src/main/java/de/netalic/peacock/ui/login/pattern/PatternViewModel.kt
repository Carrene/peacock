package de.netalic.peacock.ui.login.pattern

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import de.netalic.peacock.data.model.MyResponse
import de.netalic.peacock.ui.base.BaseViewModel

enum class ResponseStatus {
    FIRST_SUCCESS,
    SECOND_SUCCESS,
    FAILED
}

class PatternViewModel : BaseViewModel() {

    private var mCounter = 1
    private var mPattern: String? = null

    private val mResponse = MutableLiveData<MyResponse<ResponseStatus>>()

    fun getResponse(): LiveData<MyResponse<ResponseStatus>> {
        return mResponse
    }

    fun onPatternListener(pattern: String) {

        if (mCounter == 1) {
            mPattern = pattern
            ++mCounter
            mResponse.value = MyResponse.success(ResponseStatus.FIRST_SUCCESS)
        } else {
            if (mPattern == pattern) {
                mResponse.value = MyResponse.success(ResponseStatus.SECOND_SUCCESS)
            } else {
                mCounter = 1
                mPattern = null
                mResponse.value = MyResponse.success(ResponseStatus.FAILED)
            }
        }
    }
}