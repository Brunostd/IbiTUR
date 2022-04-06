package com.deny.ibitur.app.model.atividades

interface GestorAtividadesRepository {

    fun getListAtividades(): MutableList<AtividadesModel>

}