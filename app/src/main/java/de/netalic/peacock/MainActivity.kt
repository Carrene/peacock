package de.netalic.peacock

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import de.netalic.peacock.ui.base.BaseActivity
import de.netalic.peacock.ui.registeration.codeverification.CodeVerificationFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private val mToolbarTitle by lazy { textView_mainActivity_toolbarTitle }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.beginTransaction().add(R.id.frameLayout_mainActivity_fragmentContainer,
            CodeVerificationFragment()).commit()
    }

    override fun getLayoutId(): Int {

        return R.layout.activity_main
    }

    override fun setToolbar() {

        mToolbarTitle.text=getString(R.string.codeVerification_steps)

    }

}
