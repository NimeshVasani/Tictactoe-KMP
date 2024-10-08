package org.nmvasani.tictactoe.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.nmvasani.tictactoe.viewmodels.SinglePlayerViewModel


actual val viewModelModule = module {
    singleOf(::SinglePlayerViewModel)
}