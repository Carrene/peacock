package de.netalic.peacock.ui.emailVerification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import de.netalic.peacock.data.exception.*
import de.netalic.peacock.data.model.EmailVerificationModel
import de.netalic.peacock.data.model.MyResponse
import de.netalic.peacock.data.repository.EmailRepository
import de.netalic.peacock.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class EmailVerificationViewModel(private val repository: EmailRepository) : BaseViewModel() {

    private val mSetEmailResponseLivadata = MutableLiveData<MyResponse<EmailVerificationModel>>()

    fun getSetEmailLiveData(): LiveData<MyResponse<EmailVerificationModel>> {
        return mSetEmailResponseLivadata
    }

    fun setEmail(token: String, email: String) {
        val disposable = repository.setEmail(token, email)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { mSetEmailResponseLivadata.value = MyResponse.loading() }
            .subscribe({
                when (it.code()) {
                    200 -> mSetEmailResponseLivadata.value = MyResponse.success(it.body()!!)
                    400 -> mSetEmailResponseLivadata.value = MyResponse.failed(EmailMissingException())
                    401 -> mSetEmailResponseLivadata.value = MyResponse.failed(UnauthorizedException())
                    405 ->mSetEmailResponseLivadata.value = MyResponse.failed(MethodNotAllowedException())
                    712 -> mSetEmailResponseLivadata.value = MyResponse.failed(InvalidEmailException())
                    717 -> mSetEmailResponseLivadata.value = MyResponse.failed(EmailAlreadyActivatedException())
                    718 -> mSetEmailResponseLivadata.value = MyResponse.failed(EmailAlreadyExistException())
                }
            }, { throwable ->
                mSetEmailResponseLivadata.value = MyResponse.failed(throwable)
            })
        mCompositeDisposable.add(disposable)

    }
}