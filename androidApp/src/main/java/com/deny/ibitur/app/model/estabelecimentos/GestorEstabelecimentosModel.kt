package com.deny.ibitur.app.model.estabelecimentos

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class GestorEstabelecimentosModel {

    var db = Firebase.firestore

    fun getListaEstabelecimentos(recebeString: String): MutableList<EstabelecimentosModel>{

        var listaEstabelecimentos: MutableList<EstabelecimentosModel> = arrayListOf()

        db.collection(recebeString)
            .whereEqualTo("tipoEstabelecimento", recebeString)
            .get()
            .addOnSuccessListener { result ->
                for (documents in result){
                    var note = documents.toObject(EstabelecimentosModel::class.java)

                    var p: EstabelecimentosModel = EstabelecimentosModel(
                        nomeEstabelecimento = note!!.nomeEstabelecimento,
                        cidadeEstabelecimento = note!!.cidadeEstabelecimento
                    )
                    listaEstabelecimentos.add(p)
                }
            }
        return listaEstabelecimentos
    }

    fun getExploreOutros(): MutableList<EstabelecimentosModel>{

        var listaExploreOutros: MutableList<EstabelecimentosModel> = arrayListOf()

        db.collection("estabelecimentos")
            .get()
            .addOnSuccessListener { result ->
                for (documents in result){
                    var note = documents.toObject(EstabelecimentosModel::class.java)

                    var p: EstabelecimentosModel = EstabelecimentosModel(
                        nomeEstabelecimento = note!!.nomeEstabelecimento,
                        cidadeEstabelecimento = note!!.cidadeEstabelecimento
                    )
                    listaExploreOutros.add(p)
                }
            }
        return listaExploreOutros
    }

    fun getRecuperarEstabelecimentoPorCidade(cidadeEstabelecimento: String): MutableList<EstabelecimentosModel>{

        var listaEsbelecimentos: MutableList<EstabelecimentosModel> = arrayListOf()

        db.collection("estabelecimentos")
            .whereEqualTo("cidadeEstabelecimento", cidadeEstabelecimento)
            .get()
            .addOnSuccessListener { result ->
                for (documents in result) {
                    var note = documents.toObject(EstabelecimentosModel::class.java)

                    listaEsbelecimentos.add(note)
                }
            }
        return listaEsbelecimentos
    }

}