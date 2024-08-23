package org.nmvasani.tictactoe.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.nmvasani.tictactoe.viewmodels.SinglePlayerViewModel
import org.nmvasani.tictactoe.viewmodels.MultiplayerViewModel
import org.nmvasani.tictactoe.viewmodels.SettingViewModel
import org.nmvasani.tictactoe.repositories.SinglePlayerRepository
import org.nmvasani.tictactoe.repositories.MultiplayerRepository

actual val viewModelModule = module {
    singleOf(::SinglePlayerViewModel)
    singleOf(::MultiplayerViewModel)
    singleOf(::SettingViewModel)
    singleOf(::SinglePlayerRepository)
    singleOf(::MultiplayerRepository)
}