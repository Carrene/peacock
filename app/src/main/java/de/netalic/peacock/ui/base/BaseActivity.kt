package de.netalic.peacock.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

abstract class BaseActivity:AppCompatActivity() {

    abstract fun getLayoutId():Int
    abstract fun setToolbar()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())

    }
}