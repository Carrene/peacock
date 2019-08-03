package de.netalic.peacock.ui.registration

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import de.netalic.peacock.R
import de.netalic.peacock.data.model.Status
import de.netalic.peacock.data.model.UserModel
import de.netalic.peacock.ui.base.BaseFragment
import de.netalic.peacock.util.CommonUtils
import kotlinx.android.synthetic.main.fragment_codeverification.*
import org.koin.android.viewmodel.ext.android.viewModel


class CodeVerificationFragment : BaseFragment(){


    private var mIsRunning: Boolean = false

    private lateinit var mView: View

    private val mCodeVerificationViewModel: CodeVerificationViewModel by viewModel()

    private val mTextViewIn by lazy { textView_codeVerification_in }
    private val mTextViewResendCode by lazy { textView_codeVerification_resendTitle }
    private val mPinEntryEditText by lazy { pinEntryEditText_codeVerification_setPin }
    private val mButton by lazy { button_codeVerification_continue }
    private val mTextViewTimer by lazy { textView_codeVerification_resendTime }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        mView = inflater.inflate(R.layout.fragment_codeverification, container, false)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        disableButton()
        mCodeVerificationViewModel.setTimer()
    }


    private fun buttonListener() {

        mButton.setOnClickListener { bind() }

    }

    private fun resendCodeListener() {

        mTextViewResendCode.setOnClickListener {

            if (!mIsRunning) {
                mCodeVerificationViewModel.setTimer()
                mTextViewTimer.visibility = View.VISIBLE
                mTextViewIn.visibility = View.VISIBLE
                //We have to call claim here

            }
        }
    }

    override fun initUiComponents() {

    }

    override fun initUiListeners() {

        buttonListener()
        resendCodeListener()
        pinEntryEditTextListener()
    }

    private fun pinEntryEditTextListener() {

        mPinEntryEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                if (p0!!.length == 6) {

                    enableButton()
                    CommonUtils.hideSoftKeyboard(requireActivity())
                } else {
                    disableButton()
                }
            }
        })
    }

    override fun initObserver() {

        observeBindLiveData()
        observerTimerLiveData()
    }


    private fun bind() {

        mCodeVerificationViewModel.bind(
            UserModel(
                mName = "salimi",
                mPhone = "+989211499302",
                mUdid = "D89707AC55BAED9E8F23B826FB2A28E96095A190",
                mFirebaseToken = "eyJ0eXAiOiAiSldUIiwgImFsZyI6ICJSUzI1NiIsICJraWQiOiAiODk0NTkyNzQzMzlkMzNlZmNmNTE3MD" +
                        "c4NGM5ZGU1MjUzMjEyOWVmZiJ9.eyJpc3MiOiAiZmlyZWJhc2UtYWRtaW5zZGstaXp1MTNAYWxwaGEtZDY0ZTQuaWFtLmdz" +
                        "ZXJ2aWNlYWNjb3VudC5jb20iLCAic3ViIjogImZpcmViYXNlLWFkbWluc2RrLWl6dTEzQGFscGhhLWQ2NGU0LmlhbS5nc" +
                        "2VydmljZWFjY291bnQuY29tIiwgImF1ZCI6ICJodHRwczovL2lkZW50aXR5dG9vbGtpdC5nb29nbGVhcGlzLmNvbS9n" +
                        "b29nbGUuaWRlbnRpdHkuaWRlbnRpdHl0b29sa2l0LnYxLklkZW50aXR5VG9vbGtpdCIsICJ1aWQiOiAiKzk4OTIxMTQ5O" +
                        "TMwMiIsICJpYXQiOiAxNTY0ODA4NDg2LCAiZXhwIjogMTU2NDgxMjA4Nn0.JE9aGvNfgHpBJVTlhoMoBqngpg4yia624O" +
                        "FZomnhgfA1-cywwjfT9Zpz1SSaQy0-Ldy_x_EHsq4w856QfHaqAeve8jl2UuvYNz54m8HfUAuiYnbnQJrtaZx7ybCOJn5" +
                        "QJf1PaRC0Zu0ZJDznV-xa6a3t7yA2T6acEzCy576HSb1CBF_E24NaVH-s5z0JgEXqhH6KlsO_zMo8vF7nhme9EcPxDYaC" +
                        "dm8LMg93oce2vjs63EdbrUuv_ilOpVjC4ziRTBbTtLX9NLLlgcEvHyN0uoeYsw2FfMlF6JDxDchvraRGQVGJjjATXcVJzg" +
                        "Clq2dzMJd27Oeo35MFq24voNt3lw",
                mActivateToken = "700497",
                mDeviceType = "android"
            )
        )
    }


    private fun observeBindLiveData() {

        mCodeVerificationViewModel.getBindLiveData().observe(this, Observer {

            when (it.status) {
                Status.LOADING -> {
                }
                Status.SUCCESS -> {
                }
                Status.FAILED -> {
                }
            }
        })
    }

    private fun observerTimerLiveData() {

        mCodeVerificationViewModel.getTimerLiveData().observe(this, Observer {

            when (it.data) {

                getString(R.string.codeVerification_resendCode) -> {

                    onFinish()
                }

                else -> {

                    onTick(it.data!!)
                }
            }
        })
    }

    private fun onTick(time: String) {

        mTextViewResendCode.isEnabled = false
        mTextViewTimer.text = time

    }

    private fun onFinish() {

        mTextViewResendCode.isEnabled = true
        mIsRunning = false
        if (context != null) {

            mTextViewIn.visibility = View.GONE
            mTextViewTimer.visibility = View.GONE
        }
    }

    private fun disableButton() {

        mButton.isEnabled = false
    }

    private fun enableButton() {

        mButton.isEnabled = true
    }
}