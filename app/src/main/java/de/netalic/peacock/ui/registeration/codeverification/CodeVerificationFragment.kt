package de.netalic.peacock.ui.registeration.codeverification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import de.netalic.peacock.R
import de.netalic.peacock.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_codeverification.*

class CodeVerificationFragment : Fragment() {

    private  lateinit var mView: View

    private val mTextViewPhoneNumber by lazy { textView_codeVerification_phoneNumber }
    private val mPinEntryEditText by lazy { pinEntryEditText_codeVerification_setPin }
    private val mButton by lazy { button_codeVerification_continue }
    private val mTextViewTimer by lazy { textView_codeVerification_resendTime }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        mView=inflater.inflate(R.layout.fragment_codeverification,container,false)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUiListener()

    }

    private fun initUiListener(){


    }


}