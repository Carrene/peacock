package de.netalic.peacock.ui.base

import de.netalic.peacock.R
import kotlinx.android.synthetic.main.activity_mainhost.*

class MainHostActivity : BaseActivity() {

    private val mToolbar by lazy { toolbar_mainHost_toolbar }
    private val mTextViewToolbarTitle by lazy { textView_mainHost_toolbarTitle }

    override fun getLayoutResourceId() = R.layout.activity_mainhost

    override fun initUiComponents() {
        setSupportActionBar(mToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun initUiListeners() {}

    fun updateToolbarTitle(title: String) {
        mTextViewToolbarTitle.text = title
    }

}
