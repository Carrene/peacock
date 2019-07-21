package de.netalic.peacock.ui.login.pattern

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import de.netalic.peacock.R
import kotlinx.android.synthetic.main.activity_mainhost.*

class MainHostActivity : AppCompatActivity() {

    private val mToolbar by lazy { toolbar_mainHost }
    private val mTextViewToolbarTitle by lazy { textView_mainHost_toolbarTitle }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mainhost)
        initUiComponents()
    }

    private fun initUiComponents() {
        setSupportActionBar(mToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    fun updateToolbarTitle(title: String) {
        mTextViewToolbarTitle.text = title
    }

}
