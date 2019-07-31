package de.netalic.peacock.di

import de.netalic.peacock.data.repository.EmailRepository
import de.netalic.peacock.data.repository.UserRepository
import de.netalic.peacock.data.webservice.ApiClient
import de.netalic.peacock.ui.emailVerification.EmailVerificationViewModel
import de.netalic.peacock.ui.login.pattern.PatternViewModel
import de.netalic.peacock.ui.registration.RegistrationViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val repositoryModule = module {
    single { UserRepository(get()) }
    single { EmailRepository(get()) }
}

val viewModelModule = module {
    viewModel { PatternViewModel() }
    viewModel { RegistrationViewModel(get()) }
    viewModel { EmailVerificationViewModel(get()) }


}

val apiModule = module {
    single { ApiClient.getService() }
}