package de.netalic

import android.app.Application
import de.netalic.peacock.di.apiModule
import de.netalic.peacock.di.repositoryModule
import de.netalic.peacock.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApp : Application() {


    override fun onCreate() {
        super.onCreate()

        instance = this

        startKoin {

            androidLogger()
            androidContext(this@MyApp)

            modules(
                listOf(
                repositoryModule,
                apiModule,
                viewModelModule
            )
            )
        }
    }

    companion object {

        lateinit var instance: Application
    }
}