package de.netalic.peacock

import android.app.Application
import de.netalic.peacock.di.apiModule
import de.netalic.peacock.di.repositoryModule
import de.netalic.peacock.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class PeacockApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@PeacockApplication)
            modules(
                listOf(
                    repositoryModule,
                    viewModelModule,
                    apiModule
                )
            )
        }
    }

}