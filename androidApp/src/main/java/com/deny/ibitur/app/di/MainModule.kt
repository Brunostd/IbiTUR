package com.deny.ibitur.app.di

import com.deny.ibitur.app.model.atividades.GestorAtividadesModel
import com.deny.ibitur.app.model.atividades.GestorAtividadesRepository
import com.deny.ibitur.app.model.carrosel.GestorCarroselModel
import com.deny.ibitur.app.model.carrosel.GestorCarroselRepository
import com.deny.ibitur.app.ui.fragments.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val atividadeModule = module {

    single<GestorAtividadesRepository> { GestorAtividadesModel() }

    viewModel { HomeViewModel(get()) }
}

val carroselModule = module {

    single<GestorCarroselRepository> { GestorCarroselModel() }

    viewModel{ HomeViewModel(get()) }

}