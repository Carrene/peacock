package de.netalic.peacock.ui.registeration.codeverification

import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import de.netalic.peacock.R
import de.netalic.peacock.data.model.Status
import de.netalic.peacock.data.model.User
import de.netalic.peacock.ui.base.BaseFragment
import de.netalic.peacock.ui.util.CommonUtils
import kotlinx.android.synthetic.main.fragment_codeverification.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit


class CodeVerificationFragment : BaseFragment() {

    companion object {

        var sTimer = 30000
    }

    private var mIsRunning: Boolean = false
    private lateinit var mCountDownTimer: CountDownTimer
    private lateinit var mView: View

    private val mCodeVerificationViewModel: CodeVerificationViewModel by viewModel()

    private val mPinEntryEditText by lazy { pinEntryEditText_codeVerification_setPin }
    private val mButton by lazy { button_codeVerification_continue }
    private val mTextViewTimer by lazy { textView_codeVerification_resendTime }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        mView = inflater.inflate(R.layout.fragment_codeverification, container, false)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setTimer()
        disableButton()

    }


    override fun initUiListener() {

        mButton.setOnClickListener { bind() }

        mTextViewTimer.setOnClickListener {

            if (!mIsRunning) {
                setTimer()
                //We have to call claim here

            }
        }


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

    override fun initUiComponent() {

        observeViewModel()

    }

    private fun bind() {

        mCodeVerificationViewModel.bind(
            User(
                "+989211499302",
                "D89707AC55BAED9E8F23B826FB2A28E96095A190",
                "salimi",
                "android",
                "eyJ0eXAiOiAiSldUIiwgImFsZyI6ICJSUzI1NiIsICJraWQiOiAiODk0NTkyNzQzMzlkMzNlZmNmNTE3MDc4NGM5ZGU1MjUzMjEyOWVmZiJ9.eyJpc3MiOiAiZmlyZWJhc2UtYWRtaW5zZGstaXp1MTNAYWxwaGEtZDY0ZTQuaWFtLmdzZXJ2aWNlYWNjb3VudC5jb20iLCAic3ViIjogImZpcmViYXNlLWFkbWluc2RrLWl6dTEzQGFscGhhLWQ2NGU0LmlhbS5nc2VydmljZWFjY291bnQuY29tIiwgImF1ZCI6ICJodHRwczovL2lkZW50aXR5dG9vbGtpdC5nb29nbGVhcGlzLmNvbS9nb29nbGUuaWRlbnRpdHkuaWRlbnRpdHl0b29sa2l0LnYxLklkZW50aXR5VG9vbGtpdCIsICJ1aWQiOiAiKzk4OTIxMTQ5OTMwMiIsICJpYXQiOiAxNTYzOTYwNDU3LCAiZXhwIjogMTU2Mzk2NDA1N30.HOUVBzwbmGwsglQHukGwrijlUuSZ241KdN2Eol3Gy80mmd4Kxoc58m3VhL71AWv3WS99eE7uz6xctl--yLPilhN3WJ_z2nxySqkhxiZ9OtaH_U8sTek63SJgfINeTFzJFpWHkT_DlQNPTVoH_AqbXjh0gZwdpVdMyoLmmuJf-WIqx2y7BdwudCTiAqY_RoK7DdDwS8Jf28J-czpWi7Q4neUo1pC0WLi986u9n0mZcfIhWoVB_fV0A2-fWRV6yhT647sfHntC2eSg-OJZKO-MAyBsgKDIZm_ubX7m3LHD6rahpnUHtY8m33eJyD-EfZcKboRWalJkmje69abirvep1A",
                "082016"
            )
        )

    }

    private fun observeViewModel() {

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

    private fun setTimer() {


        mCountDownTimer = object : CountDownTimer(sTimer.toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {

                val minuteTimer = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
                val secondTimer = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
                )
                mTextViewTimer.text = String.format("%02d:%02d ", minuteTimer, secondTimer)
                mTextViewTimer.isEnabled = false
                mTextViewTimer.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorText))

            }

            override fun onFinish() {

                mTextViewTimer.isEnabled = true
                mIsRunning = false
                if (context != null) {
                    mTextViewTimer.text = getString(R.string.codeVerification_resendCode)
                    mTextViewTimer.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorTertiaryDark))
                }
            }
        }.start()

    }

    private fun disableButton() {

        mButton.isEnabled = false
    }

    private fun enableButton() {

        mButton.isEnabled = true
    }

}