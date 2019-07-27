package de.netalic.peacock.ui.registration

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.ehsanmashhadi.library.view.CountryPicker
import com.google.android.material.snackbar.Snackbar
import de.netalic.peacock.R
import de.netalic.peacock.ui.base.BaseFragment
import de.netalic.peacock.ui.main.MainActivity
import de.netalic.peacock.util.CommonUtils
import de.netalic.peacock.util.CustomPhoneFormatTextWatcher
import de.netalic.peacock.util.PhoneInfo
import kotlinx.android.synthetic.main.fragment_registration.*
import org.koin.android.viewmodel.ext.android.viewModel


class RegistrationFragment : BaseFragment(){
    //ToDo min and max size of phone number
    private val mPatternMatcher = "[0-9 ]{5,15}".toRegex()
    private lateinit var mCustomPhoneFormatTextWatcher: CustomPhoneFormatTextWatcher

    private lateinit var mViewRoot: View
    private val mPhoneInputViewModel: RegistrationViewModel by viewModel()
    private val mPhoneInputEditText by lazy { editText_registration_phoneInput }
    private val mCountryFlagImageView by lazy { imageButton_registration_flags }
    private val mCountryCode by lazy { textView_registration_countryCode }
    private val mContinueButton by lazy { button_registration_continue }

    private var countryCode = "IR"
    private var dialCode = "+98"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mViewRoot = inflater.inflate(R.layout.fragment_registration, container, false)
        return mViewRoot
    }

    override fun initUiListeners() {
        mCountryFlagImageView.setOnClickListener { countryPicker() }
        mContinueButton.setOnClickListener { claim() }
        mPhoneInputEditText.setOnKeyListener { _, keyCode, keyEvent ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.action
                == KeyEvent.ACTION_DOWN
            ) {
                mContinueButton.callOnClick()
                CommonUtils.hideSoftKeyboard(requireActivity())
                true
            } else
                false
        }
    }

    override fun initComponents() {
        initToolbar()
        phoneInputEditTextWatcher()
        phoneFormat()
        initObserver()
    }

    private fun initToolbar() {
        val activity = requireActivity()
        if (activity is MainActivity) {
            activity.updateToolbarTitle(getString(R.string.patternLogin_stepNOfFour, "2"))
        }
    }

    private fun initObserver() {
        mPhoneInputViewModel.getClaimLiveData().observe(this, Observer {

            //when (it.status) {
            Snackbar.make(mViewRoot, it.status.toString(), Snackbar.LENGTH_LONG).show()
            //ToDo get all status for
            //}
        })
    }

    private fun claim() {
        val inputEditText = mPhoneInputEditText.text
        if (mPatternMatcher.matches(inputEditText)) {
            val phoneNumber = countryCode + mPhoneInputEditText.text.toString()
            mPhoneInputViewModel.claim(phoneNumber, PhoneInfo.getPhoneUdid(requireContext()))
        }
    }

    private fun countryPicker() {
        val countryPicker = CountryPicker.Builder(requireContext()).setCountrySelectionListener { country ->

            countryCode = country.code
            dialCode = country.dialCode
            changeCountryImage(country.flagName)
            setCodeDialText()
            mPhoneInputEditText.setText("")


        }.showingFlag(true)
            .showingDialCode(true)
            .enablingSearch(true)
            .setPreSelectedCountry("iran")
            .setViewType(CountryPicker.ViewType.BOTTOMSHEET)
            .build()

        countryPicker.show(activity as AppCompatActivity?)
    }


    private fun changeCountryImage(flagName: String) {
        val iconResId = resources.getIdentifier(flagName, "drawable", activity?.packageName)
        mCountryFlagImageView.setImageResource(iconResId)

    }

    private fun setCodeDialText() {
        val dialCodeText =
            getString(R.string.country_Code, dialCode)
        mCountryCode.text = dialCodeText
    }

    private fun phoneInputEditTextWatcher() {
        mPhoneInputEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(characters: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (characters != null && mPatternMatcher.matches(characters))
                    enableContinueButton()
                else
                    disableContinueButton()

            }

        })
    }

    private fun phoneFormat() {
        mCustomPhoneFormatTextWatcher = CustomPhoneFormatTextWatcher(countryCode, dialCode)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mPhoneInputEditText.removeTextChangedListener(mCustomPhoneFormatTextWatcher)
            mPhoneInputEditText.addTextChangedListener(mCustomPhoneFormatTextWatcher)
        }
    }

    private fun enableContinueButton() {
        mContinueButton.isEnabled = true
        mContinueButton.setBackgroundColor(resources.getColor(R.color.purple))
    }

    private fun disableContinueButton() {
        mContinueButton.isEnabled = false
        mContinueButton.setBackgroundColor(resources.getColor(R.color.buttonDisable))
    }


}