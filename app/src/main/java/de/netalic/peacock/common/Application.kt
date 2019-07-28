package de.netalic.peacock.common

import android.app.Application
import de.netalic.peacock.BuildConfig
import de.netalic.peacock.di.patternViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        startKoin {
            androidLogger()
            androidContext(this@Application)
            modules(listOf(patternViewModelModule))
        }
    }

}