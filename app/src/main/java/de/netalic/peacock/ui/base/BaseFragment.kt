package de.netalic.peacock.ui.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUiComponents()
        initUiListeners()
    }

    protected abstract fun initUiComponents()
    protected abstract fun initUiListeners()

}