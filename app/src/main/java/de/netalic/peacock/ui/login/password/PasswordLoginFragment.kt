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
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import de.netalic.peacock.R
import de.netalic.peacock.data.exception.NoDigitException
import de.netalic.peacock.data.exception.NoSpecialCharacterException
import de.netalic.peacock.data.exception.NoUppercaseException
import de.netalic.peacock.data.exception.NotMinimumCharactersException
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
        mImageViewProfile.setImageResource(R.drawable.temp_profilephoto_placeholder)
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
                        val (start, end) = mViewModel.getSpanRange(message, messageParts[0])
                        setColorSpan(spannableString, successColor, start, end)
                    }
                    ResponseStatus.SUCCESS_UPPERCASE -> {
                        val (start, end) = mViewModel.getSpanRange(message, messageParts[1])
                        setColorSpan(spannableString, successColor, start, end)
                    }
                    ResponseStatus.SUCCESS_DIGIT -> {
                        val (start, end) = mViewModel.getSpanRange(message, messageParts[2])
                        setColorSpan(spannableString, successColor, start, end)
                    }
                    ResponseStatus.SUCCESS_SPECIAL_CHAR -> {
                        val (start, end) = mViewModel.getSpanRange(message, messageParts[3])
                        setColorSpan(spannableString, successColor, start, end)
                    }
                    else -> {}
                }

            }

            else if (response.status == Status.FAILED) {
                val throwableMessage = response.throwable?.message ?: return@Observer
                when (throwableMessage) {
                    NotMinimumCharactersException.MESSAGE -> {
                        val (start, end) = mViewModel.getSpanRange(message, messageParts[0])
                        setColorSpan(spannableString, errorColor, start, end)
                    }
                    NoUppercaseException.MESSAGE -> {
                        val (start, end) = mViewModel.getSpanRange(message, messageParts[1])
                        setColorSpan(spannableString, errorColor, start, end)
                    }
                    NoDigitException.MESSAGE -> {
                        val (start, end) = mViewModel.getSpanRange(message, messageParts[2])
                        setColorSpan(spannableString, errorColor, start, end)
                    }
                    NoSpecialCharacterException.MESSAGE -> {
                        val (start, end) = mViewModel.getSpanRange(message, messageParts[3])
                        setColorSpan(spannableString, errorColor, start, end)
                    }
                }
            }
            mTextViewPasswordRules.text = spannableString
        })
    }

    private fun initPasswordRepeatObserver() {
        mViewModel.getRepeatResponse().observe(this, Observer {

        })
    }

    private fun setColorSpan(spannableString: SpannableString, color: Int, start: Int, end: Int) {
        spannableString.setSpan(ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }

    private fun initPasswordEqualityObserver() {
        mViewModel.getEqualityResponse().observe(this, Observer { response ->
            if (response.status == Status.FAILED) {
                mButtonContinue.isEnabled = false
                mButtonContinue.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.colorPrimaryDark)
            } else {
                mButtonContinue.isEnabled = true
                mButtonContinue.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.colorTertiary)
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
        override fun afterTextChanged(editable: Editable?) {
            val input = editable?.toString() ?: return
            mViewModel.onPasswordEntered(input)
        }
        override fun beforeTextChanged(charsequence: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(charsequence: CharSequence?, start: Int, before: Int, count: Int) {}
    }

    inner class PasswordRepeatListener : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            val input = editable?.toString() ?: return
            mViewModel.onPasswordRepeated(input)
        }
        override fun beforeTextChanged(charsequence: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(charsequence: CharSequence?, start: Int, before: Int, count: Int) {}
    }

}