package de.netalic.peacock

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import de.netalic.peacock.ui.base.BaseActivity
import de.netalic.peacock.ui.registeration.codeverification.CodeVerificationFragment

class MainActivity : BaseActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.beginTransaction().add(R.id.frameLayout_mainActivity_fragmentContainer,
            CodeVerificationFragment()).commit()
    }

    override fun getLayoutId(): Int {

        return R.layout.activity_main
    }

    override fun setToolbar(title: String) {
        super.setToolbar(getString(R.string.codeVerification_steps))
    }

}
