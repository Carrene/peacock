package de.netalic.peacock.ui.registration

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.ehsanmashhadi.library.view.CountryPicker
import de.netalic.peacock.R
import de.netalic.peacock.ui.base.BaseFragment
import de.netalic.peacock.util.Converter
import kotlinx.android.synthetic.main.fragment_registration.*


class RegistrationFragment : BaseFragment() {

    companion object {
        const val mPhoneNumberMinLegnth = 5
    }

    private val mPhoneInputEditText by lazy { editText_registration_phoneInput }
    private val mCountryFlagButton by lazy { imagebutton_registration_flags }
    private val mCountryCode by lazy { textView_registration_countryCode }
    private val mContinueButton by lazy { button_registarion_continue }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun initUiListeners() {
        mCountryFlagButton.setOnClickListener {
            countryPicker()
        }

    }

    override fun initUiComponent() {
        phoneInputEdittextWatcher()
    }

    private fun countryPicker() {
        val countryPicker = CountryPicker.Builder(context).setCountrySelectionListener { country ->

            changeCountryImageSize(country.flagName)
            setCodeDialText(country.dialCode)
        }.showingFlag(true)
            .showingDialCode(true)
            .enablingSearch(true)
            .setPreSelectedCountry("iran")
            .build()

        countryPicker.show(activity as AppCompatActivity?)
    }


    private fun changeCountryImageSize(flagName: String) {
        val iconResId = resources.getIdentifier(flagName, "drawable", activity?.packageName)
        val params = mCountryFlagButton.layoutParams as ViewGroup.LayoutParams
        params.width = context?.let{Converter.dpToPx(28,it)}?:0
        params.height = context?.let{Converter.dpToPx(20,it)}?:0
// existing height is ok as is, no need to edit it
        mCountryFlagButton.layoutParams = params
        mCountryFlagButton.setImageResource(iconResId)

    }

    private fun setCodeDialText(dialCode: String) {
        val dialCodeText =
            getString(R.string.paranteses_left) + dialCode + getString(R.string.paranteses_right)
        mCountryCode.text = dialCodeText
    }

    private fun phoneInputEdittextWatcher() {
        mPhoneInputEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(characters: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (characters != null && characters.length > mPhoneNumberMinLegnth)
                    enableContinueButton()
            }

        })
    }

    private fun enableContinueButton() {
        mContinueButton.apply {
            isEnabled = true
            setOnClickListener {
                //TODO

            }
        }
    }

}