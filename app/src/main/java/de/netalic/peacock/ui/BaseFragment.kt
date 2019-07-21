package de.netalic.peacock.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUiComponent()
        initUiListeners()
    }

    abstract fun initUiListeners()
    abstract fun initUiComponent()
}