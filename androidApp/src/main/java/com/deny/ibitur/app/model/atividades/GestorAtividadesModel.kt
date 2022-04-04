package com.deny.ibitur.app.model.atividades

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class GestorAtividadesModel {

    fun getListaAtividades(): MutableList<AtividadesModel>{

        var db = Firebase.firestore
        var listaAtividades: MutableList<AtividadesModel> = arrayListOf()

        db.collection("atividades")
            .get()
            .addOnSuccessListener { result ->
                for (documents in result){
                    var note = documents.toObject(AtividadesModel::class.java)

                    var n: AtividadesModel = AtividadesModel(
                        //imageAtividade = note!!.imageAtividade,
                        tituloAtividade = note!!.tituloAtividade
                    )
                    listaAtividades.add(n)
                }
            }

        return listaAtividades
    }
}