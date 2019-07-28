package de.netalic.peacock.di

import com.raywenderlich.android.validatetor.ValidateTor
import de.netalic.peacock.ui.login.password.PasswordLoginViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val validateTorModule = module {
    factory { ValidateTor() }
}

val passwordLogonViewModelModule = module {
    viewModel { PasswordLoginViewModel() }
}