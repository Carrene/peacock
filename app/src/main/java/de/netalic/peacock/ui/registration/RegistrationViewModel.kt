package de.netalic.peacock.ui.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import de.netalic.peacock.data.model.MyResponse
import de.netalic.peacock.data.model.UserModel
import de.netalic.peacock.data.repository.UserRepository
import de.netalic.peacock.ui.base.BaseViewModel

class RegistrationViewModel(repository: UserRepository) : BaseViewModel() {

    private val mClaimResponseLiveData = MutableLiveData<MyResponse<UserModel>>()

    fun getClaimLiveData(): LiveData<MyResponse<UserModel>> {
        return mClaimResponseLiveData
    }

}