package de.netalic.peacock.di

import de.netalic.peacock.data.repository.UserRepository
import de.netalic.peacock.data.webservice.ApiClient
import de.netalic.peacock.ui.registration.RegistrationViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

class AppModule {

    val repositoryModule = module {
        single {
            UserRepository(get())
        }
    }

    val viewModelModule = module {
        viewModel {
            RegistrationViewModel(get())
        }
    }

    val apiModule = module {
        single {
            ApiClient.getService()
        }
    }
}