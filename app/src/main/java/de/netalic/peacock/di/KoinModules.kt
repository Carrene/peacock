package de.netalic.peacock.di

import de.netalic.peacock.common.Validator
import de.netalic.peacock.ui.login.password.PasswordLoginViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val validateTorModule = module {
    factory { Validator() }
}

val passwordLogonViewModelModule = module {
    viewModel { PasswordLoginViewModel(get()) }
}