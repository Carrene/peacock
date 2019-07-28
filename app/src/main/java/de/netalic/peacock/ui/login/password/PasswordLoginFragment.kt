package de.netalic.peacock.ui.login.password

import android.os.Bundle
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.raywenderlich.android.validatetor.ValidateTor
import de.netalic.peacock.R
import de.netalic.peacock.ui.base.BaseFragment
import de.netalic.peacock.ui.base.MainHostActivity
import kotlinx.android.synthetic.main.fragment_passwordlogin.*
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.lang.Exception
import java.lang.IllegalArgumentException

class PasswordLoginFragment : BaseFragment() {

    private val mImageViewProfile by lazy { imageView_patternLogin_profile }
    private val mTextInputEditTextPassword by lazy { textInputEditText_passwordLogin_password }
    private val mTextViewPasswordRules by lazy { textView_passwordLogin_passwordRules }

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
            activity.updateToolbarTitle(getString(R.string.patternLogin_stepNOfFour, "1"))
        }
    }

    private fun initObservers() {
        val message = getString(R.string.passwordLogin_passwordRules)
        val spannableString = SpannableString(message)
        val successColor = ContextCompat.getColor(requireContext(), R.color.success)
        val errorColor = ContextCompat.getColor(requireContext(), R.color.error)
        val messageParts = message.split(",")
        Timber.tag("MessageParts").d(messageParts.size.toString())
        mViewModel.getResponse().observe(this, Observer {
            val status = it.data ?: throw IllegalArgumentException("Data is null")
            when (status) {
                ResponseStatus.PART_ONE_COMPLETED ->  {
                    val startIndex = message.indexOf(messageParts[0])
                    val endIndex = startIndex + (messageParts[0].length)
                    spannableString.setSpan(ForegroundColorSpan(successColor), startIndex, endIndex,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                }
                ResponseStatus.PART_ONE_FAILED -> {
                    val startIndex = message.indexOf(messageParts[0])
                    val endIndex = startIndex + (messageParts[0].length)
                    Timber.tag("TAGGG").d("Text: ${messageParts[0]} - Start Index: $startIndex - Last Index: $endIndex")
                    spannableString.setSpan(ForegroundColorSpan(errorColor), startIndex, endIndex,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                }
                ResponseStatus.PART_TWO_COMPLETED -> {
                    val startIndex = message.indexOf(messageParts[1])
                    val endIndex = startIndex + (messageParts[1].length)
                    spannableString.setSpan(ForegroundColorSpan(successColor), startIndex, endIndex,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                }
                ResponseStatus.PART_TWO_FAILED -> {
                    val startIndex = message.indexOf(messageParts[1])
                    val endIndex = startIndex + (messageParts[1].length)
                    spannableString.setSpan(ForegroundColorSpan(errorColor), startIndex, endIndex,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                }
                ResponseStatus.PART_THREE_COMPLETED -> {
                    val startIndex = message.indexOf(messageParts[2])
                    val endIndex = startIndex + (messageParts[2].length)
                    spannableString.setSpan(ForegroundColorSpan(successColor), startIndex, endIndex,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                }
                ResponseStatus.PART_THREE_FAILED -> {
                    val startIndex = message.indexOf(messageParts[2])
                    val endIndex = startIndex + (messageParts[2].length)
                    spannableString.setSpan(ForegroundColorSpan(errorColor), startIndex, endIndex,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                }
                /*ResponseStatus.PART_FOUR_COMPLETED -> {
                    val startIndex = message.indexOf(messageParts[3])
                    val endIndex = startIndex + ((messageParts[3].length - startIndex) + 1)
                    spannableString.setSpan(ForegroundColorSpan(successColor), startIndex, endIndex,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                }
                ResponseStatus.PART_FOUR_FAILED -> {
                    val startIndex = message.indexOf(messageParts[3])
                    val endIndex = startIndex + ((messageParts[3].length - startIndex) + 1)
                    spannableString.setSpan(ForegroundColorSpan(errorColor), startIndex, endIndex,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                }*/
            }
            mTextViewPasswordRules.text = spannableString
        })

    }

    override fun initUiListeners() {
        mTextInputEditTextPassword.addTextChangedListener(PasswordListener())
    }

    inner class PasswordListener : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            Timber.tag("afterTextChanged()").d(s.toString())
            val input = s?.toString() ?: return
            mViewModel.onPasswordEntered(input)
            /*val isLengthOkay = validator.isAtMostLength(input, 20) && validator.isAtleastLength(input, 8)
            if (isLengthOkay) {
                val hasCapital = validator.hasAtleastOneUppercaseCharacter(input)
                val hasNumber = validator.hasAtleastOneDigit(input)
                if (hasCapital && hasNumber) {
                    spannableString.setSpan(ForegroundColorSpan(successColor), 0, message.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    mTextViewPasswordRules.text = spannableString
                }
            }*/
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }

}
