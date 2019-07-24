package de.netalic.peacock.ui.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    protected abstract fun initUiComponents()
    protected abstract fun initUiListeners()
    protected abstract fun getLayoutResourceId(): Int

    final override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(getLayoutResourceId())
        initUiComponents()
        initUiListeners()
    }

}