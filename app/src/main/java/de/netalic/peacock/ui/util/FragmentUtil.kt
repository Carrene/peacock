package de.netalic.peacock.ui.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class FragmentUtil {

    companion object {
        fun replaceFragmentWithFragment(fragmentManager: FragmentManager, fragment: Fragment, frameId: Int) {

            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(frameId, fragment)
            fragmentTransaction.commit()
        }

        fun replaceFragmentWithFragmentWithAdd(fragmentManager: FragmentManager, fragment: Fragment, frameId: Int) {

            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(frameId, fragment)
            fragmentTransaction.addToBackStack(fragment.javaClass.name)
            fragmentTransaction.commit()
        }
    }

}