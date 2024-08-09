package org.nmvasani.tictactoe.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.nmvasani.tictactoe.viewmodels.MainViewModel
import org.nmvasani.tictactoe.repositories.MainRepository


actual val viewModelModule = module {
    viewModelOf(::MainViewModel)
    singleOf(::MainRepository)
}