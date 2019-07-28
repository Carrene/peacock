package de.netalic.peacock.ui.login.pattern


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.andrognito.patternlockview.PatternLockView
import com.andrognito.patternlockview.listener.PatternLockViewListener
import com.andrognito.patternlockview.utils.PatternLockUtils
import de.netalic.peacock.R
import de.netalic.peacock.ui.base.BaseFragment
import de.netalic.peacock.ui.base.MainHostActivity
import kotlinx.android.synthetic.main.fragment_patternlogin.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.KoinComponent
import timber.log.Timber


class PatternFragment : BaseFragment(), PatternLockViewListener {

    private val mImageViewProfile by lazy { imageView_patternLogin_profile }
    private val mPatternLockView by lazy { patternLockView_patternLogin_pattern }
    private val mTextViewMessage by lazy { textView_patternLogin_message }

    private val mPatternViewModel: PatternViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_patternlogin, container, false)
    }

    override fun initUiComponents() {
        updateToolbar()
        mImageViewProfile.setImageResource(R.drawable.temp)
        initObservers()
    }

    private fun initObservers() {
        mPatternViewModel.getResponse().observe(this, Observer {

            when (it.data) {
                ResponseStatus.FIRST_SUCCESS -> mTextViewMessage.text = getString(R.string.patternLogin_messageDrawAgain)
                ResponseStatus.SECOND_SUCCESS -> {
                    Toast.makeText(requireContext(), "MATCH", Toast.LENGTH_LONG).show()
                    mPatternLockView.removePatternLockListener(this)
                }
                ResponseStatus.FAILED -> {
                    Toast.makeText(requireContext(), "Error, different patterns. Try again", Toast.LENGTH_LONG).show()
                    mTextViewMessage.text = getString(R.string.patternLogin_messageDraw)
                }
            }
            mPatternLockView.clearPattern()
        })
    }

    private fun updateToolbar() {
        val activity = requireActivity()
        if (activity is MainHostActivity) {
            activity.updateToolbarTitle(getString(R.string.patternLogin_stepNOfFour, "1"))
        }
    }

    override fun initUiListeners() {
        mPatternLockView.addPatternLockListener(this)
    }

    override fun onComplete(pattern: MutableList<PatternLockView.Dot>?) {
        val result = PatternLockUtils.patternToString(mPatternLockView, pattern)
        mPatternViewModel.onPatternListener(result)
    }
    override fun onCleared() {}
    override fun onStarted() {}
    override fun onProgress(progressPattern: MutableList<PatternLockView.Dot>?) {}

}
