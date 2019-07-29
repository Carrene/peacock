package de.netalic.peacock.ui.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable


open class BaseViewModel : ViewModel() {

    var mCompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        mCompositeDisposable.clear()
    }
}