package org.nmvasani.tictactoe.di

import org.koin.core.module.dsl.singleOf
import org.koin.core.module.single
import org.koin.dsl.module
import org.nmvasani.tictactoe.viewmodels.MainViewModel
import org.nmvasani.tictactoe.repositories.MainRepository

actual val viewModelModule = module {
    singleOf(::MainViewModel)
    singleOf(::MainRepository)
}