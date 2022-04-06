package com.deny.ibitur.app.model.carrosel

interface GestorCarroselRepository {

    fun getListCarrosel(): MutableList<CarroselModel>

}