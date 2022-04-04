package com.deny.ibitur.app.ui.fragments.restaurantes

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
import com.deny.ibitur.app.adapter.EstabelecimentosAdapter
import com.deny.ibitur.app.adapter.ExploreLocaisAdapter
import com.deny.ibitur.app.databinding.RestaurantesFragmentBinding
import com.deny.ibitur.app.model.estabelecimentos.EstabelecimentosModel
import com.deny.ibitur.app.model.estabelecimentos.GestorEstabelecimentosModel
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class RestaurantesFragment : Fragment() {

    private var _binding: RestaurantesFragmentBinding? = null
    private val args: RestaurantesFragmentArgs by navArgs()
    private var storage: FirebaseStorage = FirebaseStorage.getInstance()
    var storageRef = storage.reference
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = RestaurantesFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        var restaurantesViewModel = ViewModelProvider(this).get(RestaurantesViewModel::class.java)
        var gestorEstabelecimentosModel: GestorEstabelecimentosModel = GestorEstabelecimentosModel()

        restaurantesViewModel._listaEstabelecimentos.apply {
            value = gestorEstabelecimentosModel.getListaEstabelecimentos(args.recebeAtividade)
        }

        restaurantesViewModel.listaEstabelecimentos.observe(viewLifecycleOwner){ value ->
            var linearLayoutManager: LinearLayoutManager = LinearLayoutManager(requireContext())
            linearLayoutManager.orientation = RecyclerView.HORIZONTAL
            binding.recyclerEstabelecimentos.layoutManager = linearLayoutManager
            binding.recyclerEstabelecimentos.adapter = EstabelecimentosAdapter(value)
        }

        restaurantesViewModel.listaExploreOutros.observe(viewLifecycleOwner){ value ->
            var linearLayoutManager2: LinearLayoutManager = LinearLayoutManager(requireContext())
            linearLayoutManager2.orientation = RecyclerView.VERTICAL
            val divider = MaterialDividerItemDecoration(requireContext()!!, LinearLayoutManager.VERTICAL /*or LinearLayoutManager.HORIZONTAL*/)
            binding.recycleExploreOutros.addItemDecoration(divider)
            binding.recycleExploreOutros.layoutManager = linearLayoutManager2
            binding.recycleExploreOutros.adapter = ExploreLocaisAdapter(value)
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}