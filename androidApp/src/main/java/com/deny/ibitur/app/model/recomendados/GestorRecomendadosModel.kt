package com.deny.ibitur.app.model.recomendados

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class GestorRecomendadosModel {

    fun getListaRecomendados(): MutableList<RecomendadosModel>{

        var db = Firebase.firestore
        var listaRecomendados: MutableList<RecomendadosModel> = arrayListOf()

        db.collection("recomendados")
            .get()
            .addOnSuccessListener { result ->
                for (documents in result){
                    var note = documents.toObject(RecomendadosModel::class.java)

                    var n: RecomendadosModel = RecomendadosModel(
                        //imageCidade = note!!.imageCidade,
                        nomeCidade = note!!.nomeCidade
                    )
                    listaRecomendados.add(n)
                }
            }
        return listaRecomendados
    }

}