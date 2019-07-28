package de.netalic.peacock.di

import de.netalic.peacock.ui.login.pattern.PatternViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val patternViewModelModule = module {
    viewModel { PatternViewModel() }
}