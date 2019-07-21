package de.netalic.peacock.ui.main

import android.os.Bundle
import de.netalic.peacock.R
import de.netalic.peacock.ui.base.BaseActivity
import de.netalic.peacock.ui.registration.RegistrationFragment

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().add(R.id.frameLayout_main_container,RegistrationFragment()).commit()
    }
}
