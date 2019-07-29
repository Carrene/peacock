package de.netalic.peacock.ui.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    abstract fun initUiListener()
    abstract fun initUiComponent()
    abstract fun initObserver()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUiListener()
        initUiComponent()
        initObserver()
    }

}