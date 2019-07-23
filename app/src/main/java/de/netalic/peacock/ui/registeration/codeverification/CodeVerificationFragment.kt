package de.netalic.peacock.ui.registeration.codeverification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import de.netalic.peacock.R
import de.netalic.peacock.data.model.Status
import de.netalic.peacock.data.model.User
import de.netalic.peacock.ui.base.BaseFragment
import de.netalic.peacock.ui.util.FragmentUtil
import kotlinx.android.synthetic.main.fragment_codeverification.*

class CodeVerificationFragment : BaseFragment() {


    private val mCodeVerificationViewModel= ViewModelProviders.of(this).get(CodeVerificationViewModel::class.java)

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

        observeViewModel()

    }


    override fun initUiListener() {

        mButton.setOnClickListener { bind() }
    }

    private fun bind(){

        mCodeVerificationViewModel.bind(User("9121111111","012345678901234567890123456789","salimi"
            ,"android","213213123",1234))

    }

    private fun observeViewModel(){

        mCodeVerificationViewModel.getBindLiveData().observe(this, Observer {


            when(it.status){
            Status.SUCCESS ->{    }
            Status.FAILED -> {
            }
            }

        })


    }

    private fun navigationToEmailVerification(){

       //navigate to Email verification fragment here

    }

}