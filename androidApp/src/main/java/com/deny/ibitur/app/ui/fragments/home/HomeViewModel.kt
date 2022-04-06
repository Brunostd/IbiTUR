package com.deny.ibitur.app.ui.fragments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.deny.ibitur.app.model.atividades.AtividadesModel
import com.deny.ibitur.app.model.atividades.GestorAtividadesModel
import com.deny.ibitur.app.model.atividades.GestorAtividadesRepository
import com.deny.ibitur.app.model.carrosel.CarroselModel
import com.deny.ibitur.app.model.carrosel.GestorCarroselModel
import com.deny.ibitur.app.model.carrosel.GestorCarroselRepository
import com.deny.ibitur.app.model.recomendados.GestorRecomendadosModel
import com.deny.ibitur.app.model.recomendados.RecomendadosModel

class HomeViewModel(val repo: GestorAtividadesRepository) : ViewModel() {

    private var gestorCarroselModel: GestorCarroselModel = GestorCarroselModel()
    private val _listaCarrosel = MutableLiveData<MutableList<CarroselModel>>().apply {
        value = gestorCarroselModel.getListCarrosel()
    }

    val listaCarrosel: LiveData<MutableList<CarroselModel>> = _listaCarrosel

    private var gestorAtividadesModel: GestorAtividadesModel = GestorAtividadesModel()
    private val _listaAtividades = MutableLiveData<MutableList<AtividadesModel>>().apply {
        value = repo.getListAtividades()
    }

    val listaAtividades: LiveData<MutableList<AtividadesModel>> = _listaAtividades

    private var gestorRecomendadosModel: GestorRecomendadosModel = GestorRecomendadosModel()
    private val _listaRecomendados = MutableLiveData<MutableList<RecomendadosModel>>().apply {
        value = gestorRecomendadosModel.getListaRecomendados()
    }

    val listaRecomendados: LiveData<MutableList<RecomendadosModel>> = _listaRecomendados
}