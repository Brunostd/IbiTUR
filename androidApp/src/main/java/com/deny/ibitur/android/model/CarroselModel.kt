package com.deny.ibitur.android.model

import com.google.firebase.firestore.GeoPoint

class CarroselModel(
    //var imageLugar: Int = 0,
    var nomeLugar: String = "",
    var nomeLocalidade: String = ""
) {
    var descricaoLugar: String = ""
    var entradaLocal: String = ""
    var precoLocal: String = ""
    var horarioFuncionamento: String = ""
    var latitude: Double = 0.0
    var longitude: Double = 0.0
}