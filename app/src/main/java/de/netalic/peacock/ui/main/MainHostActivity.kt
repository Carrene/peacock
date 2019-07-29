package de.netalic.peacock.ui.main

import de.netalic.peacock.R
import de.netalic.peacock.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_mainhost.*

class MainHostActivity : BaseActivity() {

    private val mToolbar by lazy { toolbar_mainHost_toolbar }
    private val mTextViewToolbarTitle by lazy { textView_mainHost_toolbarTitle }

    override fun getLayoutResourceId() = R.layout.activity_mainhost

    override fun initUiComponents() {
        setupToolbar()
    }

    private fun setupToolbar() {
        setSupportActionBar(mToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun initUiListeners() {}

    fun updateToolbarTitle(title: String) {
        mTextViewToolbarTitle.text = title
    }

}
