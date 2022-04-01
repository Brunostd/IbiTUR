package com.deny.ibitur.app.model.estabelecimentos

class EstabelecimentosModel(
    //var imageEstabelecimento: Int = 0,
    var nomeEstabelecimento: String = "",
    var cidadeEstabelecimento: String = ""
) {
    var tipoEstabelecimento: String = ""
    var descricaoTipoEstabelecimento: String = ""
    var latitude: Double = 0.0
    var longitude: Double = 0.0
    var entradaLocal: String = ""
    var horarioFuncionamento: String = ""
    var precoLocal: String = ""
}