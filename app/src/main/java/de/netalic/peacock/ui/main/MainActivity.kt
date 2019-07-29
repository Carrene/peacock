package de.netalic.peacock.ui.main

import android.os.Bundle
import de.netalic.peacock.R
import de.netalic.peacock.ui.base.BaseActivity
import de.netalic.peacock.ui.registration.RegistrationFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {

    private val mToolbar by lazy { toolbar_mainHost }
    private val mTextViewToolbarTitle by lazy { textView_mainHost_toolbarTitle }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().add(R.id.frameLayout_main_container, RegistrationFragment()).commit()
    }

    override fun initComponents() {
       initToolbar()
    }

    fun initToolbar(){
        setSupportActionBar(mToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    fun updateToolbarTitle(title: String) {
        mTextViewToolbarTitle.text = title
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
