package de.netalic.peacock.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

abstract class BaseActivity:AppCompatActivity() {

    abstract fun getLayoutId():Int
     open fun setToolbar( title:String){

         mToolbarTitle.text=title
     }

    private val mToolbarTitle by lazy { textView_mainActivity_toolbarTitle }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())

    }
}