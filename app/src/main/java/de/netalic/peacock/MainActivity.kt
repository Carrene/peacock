package de.netalic.peacock

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import de.netalic.peacock.ui.registeration.codeverification.CodeVerificationFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().add(R.id.frameLayout_mainActivity_fragmentContainer,
            CodeVerificationFragment()).commit()
    }
}
