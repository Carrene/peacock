package de.netalic.peacock.ui.login.password

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.netalic.peacock.R
import de.netalic.peacock.ui.base.BaseFragment
import de.netalic.peacock.ui.base.MainHostActivity
import kotlinx.android.synthetic.main.fragment_passwordlogin.*
import timber.log.Timber

class PasswordLoginFragment : BaseFragment() {

    private val mImageViewProfile by lazy { imageView_patternLogin_profile }
    private val mTextInputEditTextPassword by lazy { textInputEditText_passwordLogin_password }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_passwordlogin, container, false)
    }

    override fun initUiComponents() {
        val activity = requireActivity()
        if (activity is MainHostActivity) {
            activity.updateToolbarTitle(getString(R.string.patternLogin_stepNOfFour, "1"))
        }
        mImageViewProfile.setImageResource(R.drawable.temp)
    }

    override fun initUiListeners() {
        mTextInputEditTextPassword.addTextChangedListener(PasswordListener())
    }

    inner class PasswordListener : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            Timber.tag("afterTextChanged()").d(s.toString())

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            Timber.tag("beforeTextChanged()").d(s.toString())
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            Timber.tag("onTextChanged()").d(s.toString())
        }
    }

}
