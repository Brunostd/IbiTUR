package com.deny.ibitur.app.ui.fragments.cidades

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.deny.ibitur.app.model.carrosel.CarroselModel
import com.deny.ibitur.app.model.estabelecimentos.EstabelecimentosModel

class CidadesViewModel : ViewModel() {

    lateinit var _listaEstabelecimentos: MutableLiveData<MutableList<EstabelecimentosModel>>

    val listaEstabelecimentos: LiveData<MutableList<EstabelecimentosModel>> = _listaEstabelecimentos

    lateinit var _listaLugares: MutableLiveData<MutableList<CarroselModel>>

    val listaLugares: LiveData<MutableList<CarroselModel>> = _listaLugares
}