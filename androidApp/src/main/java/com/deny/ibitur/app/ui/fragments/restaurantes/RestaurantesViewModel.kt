package com.deny.ibitur.app.ui.fragments.restaurantes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.deny.ibitur.app.model.estabelecimentos.EstabelecimentosModel
import com.deny.ibitur.app.model.estabelecimentos.GestorEstabelecimentosModel

class RestaurantesViewModel: ViewModel(){
    lateinit var _listaEstabelecimentos: MutableLiveData<MutableList<EstabelecimentosModel>>

    val listaEstabelecimentos: LiveData<MutableList<EstabelecimentosModel>> = _listaEstabelecimentos

    private var gestorEstabelecimentosModel: GestorEstabelecimentosModel = GestorEstabelecimentosModel()
    private val _listaExploreOutros = MutableLiveData<MutableList<EstabelecimentosModel>>().apply {
        value = gestorEstabelecimentosModel.getExploreOutros()
    }

    val listaExploreOutros: LiveData<MutableList<EstabelecimentosModel>> = _listaExploreOutros
}