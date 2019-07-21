package de.netalic.peacock.common

import android.app.Application
import de.netalic.peacock.BuildConfig
import timber.log.Timber

class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}