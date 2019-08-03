package de.netalic.peacock.common

import android.app.Application
import de.netalic.peacock.BuildConfig
import de.netalic.peacock.di.apiModule
import de.netalic.peacock.di.repositoryModule
import de.netalic.peacock.di.viewModelModule
import okhttp3.internal.Internal.instance
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class MyApplication : Application() {


    override fun onCreate() {
        super.onCreate()

        instance = this

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(
                listOf(
                    repositoryModule,
                    viewModelModule,
                    apiModule
                )
            )
        }
    }

    companion object {

        lateinit var instance: Application
    }

}