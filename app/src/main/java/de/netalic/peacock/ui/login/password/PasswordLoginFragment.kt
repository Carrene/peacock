package de.netalic.peacock.ui.login.password

import android.os.Bundle
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import de.netalic.peacock.R
import de.netalic.peacock.data.model.Status
import de.netalic.peacock.ui.base.BaseFragment
import de.netalic.peacock.ui.main.MainHostActivity
import kotlinx.android.synthetic.main.fragment_passwordlogin.*
import org.koin.android.viewmodel.ext.android.viewModel

class PasswordLoginFragment : BaseFragment() {

    private val mImageViewProfile by lazy { imageView_patternLogin_profile }
    private val mTextInputEditTextPassword by lazy { textInputEditText_passwordLogin_password }
    private val mTextInputEditTextRepeatPassword by lazy { textInputEditText_passwordLogin_repeatPassword }
    private val mTextViewPasswordRules by lazy { textView_passwordLogin_passwordRules }
    private val mButtonContinue by lazy { materialButton_passwordLogin_continue }
    private val mTextViewSkipToPattern by lazy { textView_patternLogin_skipToPattern }

    private val mViewModel: PasswordLoginViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_passwordlogin, container, false)
    }

    override fun initUiComponents() {
        updateToolbar()
        mImageViewProfile.setImageResource(R.drawable.temp)
        initObservers()
    }

    private fun updateToolbar() {
        val activity = requireActivity()
        if (activity is MainHostActivity) {
            activity.updateToolbarTitle(getString(R.string.all_stepNOfFour, "1"))
        }
    }

    private fun initObservers() {
        initPasswordObserver()
        initPasswordRepeatObserver()
        initPasswordEqualityObserver()
    }

    private fun initPasswordObserver() {
        val message = getString(R.string.passwordLogin_passwordRules)
        val spannableString = SpannableString(message)
        val successColor = ContextCompat.getColor(requireContext(), R.color.success)
        val errorColor = ContextCompat.getColor(requireContext(), R.color.error)
        val messageParts = message.split(",")
        mViewModel.getResponse().observe(this, Observer { response ->

            if (response.status == Status.SUCCESS) {

                when (response.data) {
                    ResponseStatus.SUCCESS_MINIMUM_CHARS -> {
                        val startIndex = message.indexOf(messageParts[0])
                        val endIndex = startIndex + (messageParts[0].length)
                        spannableString.setSpan(ForegroundColorSpan(successColor), startIndex, endIndex,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    }
                    ResponseStatus.SUCCESS_UPPERCASE -> {
                        val startIndex = message.indexOf(messageParts[1])
                        val endIndex = startIndex + (messageParts[1].length)
                        spannableString.setSpan(ForegroundColorSpan(successColor), startIndex, endIndex,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    }
                    ResponseStatus.SUCCESS_DIGIT -> {
                        val startIndex = message.indexOf(messageParts[2])
                        val endIndex = startIndex + (messageParts[2].length)
                        spannableString.setSpan(ForegroundColorSpan(successColor), startIndex, endIndex,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    }
                    ResponseStatus.SUCCESS_SPECIAL_CHAR -> {
                        val startIndex = message.indexOf(messageParts[3])
                        val endIndex = startIndex + (messageParts[3].length)
                        spannableString.setSpan(ForegroundColorSpan(successColor), startIndex, endIndex,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    }
                    else -> {}
                }

            }

            else if (response.status == Status.FAILED) {
                val throwableMessage = response.throwable?.message ?: return@Observer
                when (throwableMessage) {
                    PasswordLoginViewModel.FAILED_MINIMUM_CHARS -> {
                        val startIndex = message.indexOf(messageParts[0])
                        val endIndex = startIndex + (messageParts[0].length)
                        spannableString.setSpan(ForegroundColorSpan(errorColor), startIndex, endIndex,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    }
                    PasswordLoginViewModel.FAILED_UPPERCASE -> {
                        val startIndex = message.indexOf(messageParts[1])
                        val endIndex = startIndex + (messageParts[1].length)
                        spannableString.setSpan(ForegroundColorSpan(errorColor), startIndex, endIndex,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    }
                    PasswordLoginViewModel.FAILED_DIGIT -> {
                        val startIndex = message.indexOf(messageParts[2])
                        val endIndex = startIndex + (messageParts[2].length)
                        spannableString.setSpan(ForegroundColorSpan(errorColor), startIndex, endIndex,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    }
                    PasswordLoginViewModel.FAILED_SPECIAL_CHAR -> {
                        val startIndex = message.indexOf(messageParts[3])
                        val endIndex = startIndex + (messageParts[3].length)
                        spannableString.setSpan(ForegroundColorSpan(errorColor), startIndex, endIndex,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    }
                }
            }
            mTextViewPasswordRules.text = spannableString
        })
    }

    private fun initPasswordRepeatObserver() {
        mViewModel.getRepeatResponse().observe(this, Observer {})
    }

    private fun initPasswordEqualityObserver() {
        mViewModel.getEqualityResponse().observe(this, Observer {
            val status = it.data ?: throw IllegalArgumentException("Data is null")
            if (status == ResponseStatus.PASSWORD_MATCH) {
                Toast.makeText(context, "Password Match", Toast.LENGTH_LONG).show()
                mButtonContinue.isEnabled = true
                mButtonContinue.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.colorTertiary)
            } else {
                mButtonContinue.isEnabled = false
                mButtonContinue.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.colorPrimaryDark)
            }
        })
    }

    override fun initUiListeners() {
        mTextInputEditTextPassword.addTextChangedListener(PasswordListener())
        mTextInputEditTextRepeatPassword.addTextChangedListener(PasswordRepeatListener())
        mTextViewSkipToPattern.setOnClickListener { findNavController()
            .navigate(R.id.action_passwordLoginFragment_to_patternFragment) }
    }

    inner class PasswordListener : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            val input = s?.toString() ?: return
            mViewModel.onPasswordEntered(input)
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }

    inner class PasswordRepeatListener : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            val input = s?.toString() ?: return
            mViewModel.onPasswordRepeated(input)
        }
        override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {}
    }

}