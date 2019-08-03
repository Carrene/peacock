package de.netalic.peacock.di

import de.netalic.peacock.data.repository.UserRepository
import de.netalic.peacock.data.webservice.ApiClient
import de.netalic.peacock.ui.login.password.PasswordLoginViewModel
import de.netalic.peacock.ui.login.pattern.PatternViewModel
import de.netalic.peacock.ui.registration.RegistrationViewModel
import de.netalic.peacock.util.PasswordValidator
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val repositoryModule = module {
    single {
        UserRepository(get())
    }
}

val viewModelModule = module {
    viewModel {
        RegistrationViewModel(get())
    }
    viewModel { PatternViewModel() }

}

val apiModule = module {
    single {
        ApiClient.getService()
    }
}

val validatorModule = module {
    factory { PasswordValidator() }
}

val passwordLogonViewModelModule = module {
    viewModel { PasswordLoginViewModel(get()) }
}