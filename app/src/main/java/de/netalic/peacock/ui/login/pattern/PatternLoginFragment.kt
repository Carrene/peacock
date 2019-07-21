package de.netalic.peacock.ui.login.pattern


import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.andrognito.patternlockview.PatternLockView
import com.andrognito.patternlockview.listener.PatternLockViewListener
import com.andrognito.patternlockview.utils.PatternLockUtils
import de.netalic.peacock.R
import de.netalic.peacock.common.GlideApp
import kotlinx.android.synthetic.main.fragment_patternlogin.*
import timber.log.Timber
import java.lang.Exception


class PatternLoginFragment : Fragment(), PatternLockViewListener {

    private val mImageViewProfile by lazy { imageView_patternLogin_profile }
    private val mPatternLockView by lazy { patternLockView_patternLogin }
    private val mTextViewMessage by lazy { textView_patternLogin_message }

    private var drawCounter = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_patternlogin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUiComponents()
        initUiListeners()
    }

    private fun initUiComponents() {
        val activity = requireActivity()
        if (activity is MainHostActivity) {
            activity.updateToolbarTitle(getString(R.string.patternLogin_stepNOfFour, "1"))
        }
        GlideApp.with(requireContext()).load(R.drawable.temp).circleCrop().into(mImageViewProfile)
    }

    private fun initUiListeners() {
        mPatternLockView.addPatternLockListener(this)
    }

    override fun onComplete(pattern: MutableList<PatternLockView.Dot>?) {
        val result = PatternLockUtils.patternToString(mPatternLockView, pattern)
        Timber.tag("OnComplete").d(result)
        mPatternLockView.clearPattern()
        mTextViewMessage.text = getString(R.string.patternLogin_messageDrawAgain)
        ++drawCounter
    }
    override fun onCleared() {
        Timber.tag("OnCleared").d("onCleared()")
    }
    override fun onStarted() {
        Timber.tag("OnStarted").d("onStarted()")
    }
    override fun onProgress(progressPattern: MutableList<PatternLockView.Dot>?) {
        val result = PatternLockUtils.patternToString(mPatternLockView, progressPattern)
        Timber.tag("onProgress").d(result)
    }

    companion object {
        @JvmStatic
        fun newInstance() = PatternLoginFragment()
    }

}
