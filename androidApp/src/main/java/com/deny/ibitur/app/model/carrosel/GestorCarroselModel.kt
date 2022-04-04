package com.deny.ibitur.app.model.carrosel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class GestorCarroselModel {

    var db = Firebase.firestore

    fun getListaCarrosel(): MutableList<CarroselModel>{

        var listaCarrosel: MutableList<CarroselModel> = arrayListOf()

        db.collection("lugares")
            .get()
            .addOnSuccessListener { result ->
                for (documents in result){
                    var note = documents.toObject(CarroselModel::class.java)

                    var p: CarroselModel = CarroselModel(
                        //imageLugar = note!!.imageLugar,
                        nomeLugar  = note!!.nomeLugar,
                        nomeLocalidade = note!!.nomeLocalidade
                    )
                    listaCarrosel.add(p)
                }
            }
        return listaCarrosel
    }

    fun getListaLugaresPorNome(recebeNome: String): MutableList<CarroselModel>{

        var listaLugares: MutableList<CarroselModel> = arrayListOf()

        db.collection("lugares")
            .whereEqualTo("nomeLocalidade", recebeNome)
            .get()
            .addOnSuccessListener { result ->
                for (documents in result){
                    var note = documents.toObject(CarroselModel::class.java)

                    listaLugares.add(note)
                }
            }
        return listaLugares
    }
}