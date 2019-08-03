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
import de.netalic.peacock.data.model.Status
import de.netalic.peacock.ui.base.BaseFragment
import de.netalic.peacock.ui.main.MainHostActivity
import de.netalic.peacock.util.CommonUtils
import de.netalic.peacock.util.ValidatorUtils
import kotlinx.android.synthetic.main.fragment_emailverification.*
import org.koin.android.viewmodel.ext.android.viewModel


class EmailVerificationFragment : BaseFragment() {

    private lateinit var mViewRoot: View
    private val mEmailVerificationViewModel: EmailVerificationViewModel by viewModel()
    private val mEmailInputEditText by lazy { editText_emailVerification_emailAddress }
    private val mContinueButton by lazy { button_emailValidation_continue }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mViewRoot = inflater.inflate(R.layout.fragment_emailverification, container, false)
        return mViewRoot
    }

    override fun initUiListeners() {
        mContinueButton.setOnClickListener {
            setEmail()
            disableContinueButton()
        }
        mEmailInputEditText.setOnKeyListener { _, keyCode, keyEvent ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.action == KeyEvent.ACTION_DOWN) {
                mContinueButton.callOnClick()
                CommonUtils.hideSoftKeyboard(requireActivity())
                true
            } else
                false
        }


        mEmailInputEditText.addTextChangedListener(emailInputEditTextWatcher())

    }

    override fun initUiComponents() {
        initToolbar()
        initObserver()
    }

    private fun initToolbar() {
        val activity = requireActivity()
        if (activity is MainHostActivity) {
            activity.updateToolbarTitle(getString(R.string.all_stepNOfFour, "4"))
        }
    }

    private fun initObserver() {
        mEmailVerificationViewModel.getSetEmailLiveData().observe(this, Observer {
            //ToDo-tina get all status for
            when (it.status) {
                Status.FAILED -> enableContinueButton()
            }
            Snackbar.make(mViewRoot, it.status.toString(), Snackbar.LENGTH_LONG).show()
        })
    }

    private fun setEmail() {
        val email = editText_emailVerification_emailAddress.text.toString()
        //TODO-tina get token from bind
        val token =
            "eyJhbGciOiJIUzI1NiIsImlhdCI6MTU2NDU2NDExOSwiZXhwIjoxNTczMTY0MTE5fQ.eyJpZCI6NCwiZGV2aWNlX2lkIjoxLCJwaG9uZSI6Iis5ODkzNTkzMjMxNzUiLCJyb2xlcyI6WyJjbGllbnQiXSwic2Vzc2lvbklkIjoiYzkwZWI0N2QtZDRiNC00ZjQ1LWIwNmYtOWVlNTMyNTEwNWJlIiwiZW1haWwiOiIiLCJpc0FjdGl2ZSI6ZmFsc2V9.X2yjaZCLfsz4AXK3fGF-vnRNk5YCKvpipE0WZEOtHkQ"
        mEmailVerificationViewModel.setEmail(token, email)
    }


    private fun emailInputEditTextWatcher():TextWatcher {
        return object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(characters: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (characters != null && ValidatorUtils.emailValidator(characters.toString()))
                    enableContinueButton()
                else
                    disableContinueButton()
            }
        }
    }

    private fun enableContinueButton() {
        mContinueButton.isEnabled = true
    }

    private fun disableContinueButton() {
        mContinueButton.isEnabled = false
    }
}