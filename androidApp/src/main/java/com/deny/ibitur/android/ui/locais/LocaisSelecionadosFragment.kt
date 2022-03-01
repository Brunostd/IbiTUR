package com.deny.ibitur.android.ui.locais

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.deny.ibitur.android.R
import com.deny.ibitur.android.databinding.LocaisSelecionadosFragmentBinding
import com.deny.ibitur.android.model.CarroselModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class LocaisSelecionadosFragment : Fragment() {

    private var _binding: LocaisSelecionadosFragmentBinding? = null
    private val args: LocaisSelecionadosFragmentArgs by navArgs()
    private var db = Firebase.firestore
    private var storage: FirebaseStorage = FirebaseStorage.getInstance()
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = LocaisSelecionadosFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        var storageRef = storage.reference

        db.collection("lugares")
            .whereEqualTo("nomeLugar",args.recebeLocal)
            .get()
            .addOnSuccessListener { result ->
                for (documents in result){
                    var note = documents.toObject(CarroselModel::class.java)

                    var p: CarroselModel = CarroselModel(
                        //imageLugar = note!!.imageLugar,
                        nomeLugar  = note!!.nomeLugar,
                        nomeLocalidade = note!!.nomeLocalidade
                    )
                    binding.textNomeLocal.text = note!!.nomeLugar
                    //binding.imageLocal.setImageResource(note!!.imageLugar)
                    var spaceRef = storageRef.child("lugares/"+note!!.nomeLugar+".jpg")
                    spaceRef.downloadUrl.addOnSuccessListener {
                        Glide.with(this).load(it).into(binding.imageLocal)
                    }
                    binding.textDescricaoLocal.text = note!!.descricaoLugar
                    binding.textEntradaLocal.text = note!!.entradaLocal
                    //this.listaCarrosel.add(p)
                }
                //binding.recyclerViewCarrosel.adapter?.notifyDataSetChanged()
            }

        return view
    }

    fun trazerLugar(){

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}