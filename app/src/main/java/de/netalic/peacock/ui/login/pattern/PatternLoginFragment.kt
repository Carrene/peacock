package de.netalic.peacock.ui.login.pattern


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.andrognito.patternlockview.PatternLockView
import com.andrognito.patternlockview.listener.PatternLockViewListener
import com.andrognito.patternlockview.utils.PatternLockUtils
import de.netalic.peacock.R
import de.netalic.peacock.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_patternlogin.*
import timber.log.Timber


class PatternLoginFragment : BaseFragment(), PatternLockViewListener {

    private val mImageViewProfile by lazy { imageView_patternLogin_profile }
    private val mPatternLockView by lazy { patternLockView_patternLogin_pattern }
    private val mTextViewMessage by lazy { textView_patternLogin_message }

    private var mDrawCounter = 1
    private var mUserPattern: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_patternlogin, container, false)
    }

    override fun initUiComponents() {
        val activity = requireActivity()
        if (activity is MainHostActivity) {
            activity.updateToolbarTitle(getString(R.string.patternLogin_stepNOfFour, "1"))
        }
        mImageViewProfile.setImageResource(R.drawable.temp)
    }

    override fun initUiListeners() {
        mPatternLockView.addPatternLockListener(this)
    }

    override fun onComplete(pattern: MutableList<PatternLockView.Dot>?) {
        val result = PatternLockUtils.patternToString(mPatternLockView, pattern)
        Timber.tag("OnComplete").d(result)
        mPatternLockView.clearPattern()
        if (mDrawCounter == 1) {
            mTextViewMessage.text = getString(R.string.patternLogin_messageDrawAgain)
            ++mDrawCounter
            mUserPattern = result
            Toast.makeText(requireContext(), result, Toast.LENGTH_LONG).show()
        } else if (mDrawCounter == 2) {
            Toast.makeText(requireContext(), result, Toast.LENGTH_LONG).show()
            if (result == mUserPattern) {
                Toast.makeText(requireContext(), "MATCH", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(requireContext(), "Error, different patterns. Try again", Toast.LENGTH_LONG).show()
            }

        }

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
