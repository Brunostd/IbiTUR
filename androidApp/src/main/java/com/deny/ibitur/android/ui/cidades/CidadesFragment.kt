package com.deny.ibitur.android.ui.cidades

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.deny.ibitur.android.R
import com.deny.ibitur.android.adapter.Carrosel2Adapter
import com.deny.ibitur.android.adapter.CarroselAdapter
import com.deny.ibitur.android.adapter.EstabelecimentosAdapter
import com.deny.ibitur.android.databinding.CidadesFragmentBinding
import com.deny.ibitur.android.model.CarroselModel
import com.deny.ibitur.android.model.EstabelecimentosModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

class CidadesFragment : Fragment() {

    private var _binding: CidadesFragmentBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val args: CidadesFragmentArgs by navArgs()

    private var storage: FirebaseStorage = Firebase.storage
    private var db: FirebaseFirestore = Firebase.firestore

    private var listaLugares: MutableList<CarroselModel> = arrayListOf()
    private var listaEstabelecimentos: MutableList<EstabelecimentosModel> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CidadesFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        var storageRef = storage.reference
        var spaceRef = storageRef.child("recomendados/"+args.recebeCidade+".jpg")

        spaceRef.downloadUrl.addOnSuccessListener {
            Glide.with(requireContext()).load(it).into(binding.imageViewCidadeRecomendada)
        }

        binding.textViewCidadeRecomendada.text = args.recebeCidade

        var linearLayoutManager: LinearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.orientation = RecyclerView.HORIZONTAL
        binding.recyclerEstabelecimentosBuscar.layoutManager = linearLayoutManager
        binding.recyclerEstabelecimentosBuscar.adapter = EstabelecimentosAdapter(listaEstabelecimentos)

        var linearLayoutManager2: LinearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager2.orientation = RecyclerView.HORIZONTAL
        binding.recyclerLugaresBuscar.layoutManager = linearLayoutManager2
        binding.recyclerLugaresBuscar.adapter = Carrosel2Adapter(listaLugares)

        return view
    }

    override fun onResume() {
        super.onResume()
        recuperarEstabelecimentos()
        recuperarLugares()
    }

    override fun onPause() {
        super.onPause()
        listaEstabelecimentos.clear()
        listaLugares.clear()
    }

    fun recuperarEstabelecimentos() {
        db.collection("estabelecimentos")
            .whereEqualTo("cidadeEstabelecimento", args.recebeCidade)
            .get()
            .addOnSuccessListener { result ->
                for (documents in result) {
                    var note = documents.toObject(EstabelecimentosModel::class.java)

                    this.listaEstabelecimentos.add(note)
                }
                binding.recyclerEstabelecimentosBuscar.adapter?.notifyDataSetChanged()
            }
    }

    fun recuperarLugares(){
        db.collection("lugares")
            .whereEqualTo("nomeLocalidade", args.recebeCidade)
            .get()
            .addOnSuccessListener { result ->
                for (documents in result){
                    var note = documents.toObject(CarroselModel::class.java)

                    this.listaLugares.add(note)
                }
                binding.recyclerLugaresBuscar.adapter?.notifyDataSetChanged()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}