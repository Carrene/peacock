package de.netalic.peacock.ui.emailVerification

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import de.netalic.peacock.R
import de.netalic.peacock.ui.base.BaseFragment
import de.netalic.peacock.ui.main.MainHostActivity
import de.netalic.peacock.util.CommonUtils
import kotlinx.android.synthetic.main.fragment_emailverification.*
import org.koin.android.viewmodel.ext.android.viewModel


class EmailVerificationFragment : BaseFragment() {
    //TODO email pattern length
    private val mPatternMatcher = "[a-zA-Z0-9._-]{2,30}+@[a-zA-Z0-9-]{2,20}+\\.[a-zA-Z.]{2,10}".toRegex()

    private lateinit var mViewRoot: View
    private val mEmailVerificationViewModel: EmailVerificationViewModel by viewModel()
    private val mEmailInputEditText by lazy { editText_emailVerification_emailAddress }
    private val mContinueButton by lazy { button_emailValidation_continue }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mViewRoot = inflater.inflate(R.layout.fragment_emailverification, container, false)
        return mViewRoot
    }

    override fun initUiListeners() {
        mContinueButton.setOnClickListener { setEmail() }
        mEmailInputEditText.setOnKeyListener { _, keyCode, keyEvent ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.action == KeyEvent.ACTION_DOWN) {
                mContinueButton.callOnClick()
                CommonUtils.hideSoftKeyboard(requireActivity())
                true
            } else
                false
        }
    }

    override fun initUiComponents() {
        initToolbar()
        emailInputEditTextWatcher()
        initObserver()
    }

    private fun initToolbar() {
        val activity = requireActivity()
        if (activity is MainHostActivity) {
            activity.updateToolbarTitle(getString(R.string.patternLogin_stepNOfFour, "4"))
        }
    }

    private fun initObserver() {
        mEmailVerificationViewModel.getSetEmailLiveData().observe(this, Observer {

            //when (it.status) {
            Snackbar.make(mViewRoot, it.status.toString(), Snackbar.LENGTH_LONG).show()
            //ToDo get all status for
            //}
        })
    }

    private fun setEmail() {
        val email = editText_emailVerification_emailAddress.text.toString()
        val token =
            "eyJhbGciOiJIUzI1NiIsImlhdCI6MTU2Mzk2Njk0OCwiZXhwIjoxNTcyNTY2OTQ4fQ.eyJpZCI6NCwiZGV2aWNlX2lkIjoxLCJwaG9uZSI6Iis5ODkzNTkzMjMxNzUiLCJyb2xlcyI6WyJjbGllbnQiXSwic2Vzc2lvbklkIjoiMTk5YmM4ZWItN2ExNC00YjBjLWI2YWMtNzQyZWQ1YTViNTk3IiwiZW1haWwiOiIiLCJpc0FjdGl2ZSI6ZmFsc2V9.sa0z7_2R-u94DlUQ0JEoaCHXi-ULaU5mJFy2KDjm_oM"
        if (mPatternMatcher.matches(email)) {
            mEmailVerificationViewModel.setEmail(token, email)
        }
    }


    private fun emailInputEditTextWatcher() {
        mEmailInputEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(characters: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (characters != null && mPatternMatcher.matches(characters))
                    enableContinueButton()
                else if (characters == null || !mPatternMatcher.matches(characters))
                    disableContinueButton()
            }

        })
    }

    private fun enableContinueButton() {
        mContinueButton.isEnabled = true
        mContinueButton.setBackgroundColor(resources.getColor(R.color.colorTertiary))
    }

    private fun disableContinueButton() {
        mContinueButton.isEnabled = false
        mContinueButton.setBackgroundColor(resources.getColor(R.color.colorPrimaryDark))
    }
}