package com.deny.ibitur.app.ui.fragments.cidades

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.deny.ibitur.app.adapter.Carrosel2Adapter
import com.deny.ibitur.app.adapter.Estabelecimentos2Adapter
import com.deny.ibitur.app.databinding.CidadesFragmentBinding
import com.deny.ibitur.app.model.carrosel.CarroselModel
import com.deny.ibitur.app.model.carrosel.GestorCarroselModel
import com.deny.ibitur.app.model.estabelecimentos.EstabelecimentosModel
import com.deny.ibitur.app.model.estabelecimentos.GestorEstabelecimentosModel
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CidadesFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        val cidadesViewModel = ViewModelProvider(this).get(CidadesViewModel::class.java)
        var gestorEstabelecimentosModel: GestorEstabelecimentosModel = GestorEstabelecimentosModel()


        var storageRef = storage.reference
        var spaceRef = storageRef.child("recomendados/"+args.recebeCidade+".jpg")

        spaceRef.downloadUrl.addOnSuccessListener {
            Glide.with(requireContext()).load(it).into(binding.imageViewCidadeRecomendada)
        }

        binding.textViewCidadeRecomendada.text = args.recebeCidade

        cidadesViewModel._listaEstabelecimentos.apply {
            value = gestorEstabelecimentosModel.getRecuperarEstabelecimentoPorCidade(args.recebeCidade)
        }

        cidadesViewModel.listaEstabelecimentos.observe(viewLifecycleOwner){ value ->
            var linearLayoutManager: LinearLayoutManager = LinearLayoutManager(requireContext())
            linearLayoutManager.orientation = RecyclerView.HORIZONTAL
            binding.recyclerEstabelecimentosBuscar.layoutManager = linearLayoutManager
            binding.recyclerEstabelecimentosBuscar.adapter = Estabelecimentos2Adapter(value)
        }

        var gestorCarroselModel: GestorCarroselModel = GestorCarroselModel()
        cidadesViewModel._listaLugares.apply {
            value = gestorCarroselModel.getListaLugaresPorNome(args.recebeCidade)
        }

        cidadesViewModel.listaLugares.observe(viewLifecycleOwner){ value ->
            var linearLayoutManager2: LinearLayoutManager = LinearLayoutManager(requireContext())
            linearLayoutManager2.orientation = RecyclerView.HORIZONTAL
            binding.recyclerLugaresBuscar.layoutManager = linearLayoutManager2
            binding.recyclerLugaresBuscar.adapter = Carrosel2Adapter(value)
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}