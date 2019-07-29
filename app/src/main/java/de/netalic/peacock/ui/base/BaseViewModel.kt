package de.netalic.peacock.ui.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel:ViewModel() {

    var mCompositDisposable=CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
    }

}