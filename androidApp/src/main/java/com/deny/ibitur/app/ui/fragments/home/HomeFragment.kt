package com.deny.ibitur.app.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.deny.ibitur.app.R
import com.deny.ibitur.app.adapter.AtividadesAdapter
import com.deny.ibitur.app.adapter.CarroselAdapter
import com.deny.ibitur.app.adapter.RecomendadosAdapter
import com.deny.ibitur.app.databinding.FragmentHomeBinding
import com.deny.ibitur.app.model.atividades.AtividadesModel
import com.deny.ibitur.app.model.carrosel.CarroselModel
import com.deny.ibitur.app.model.recomendados.RecomendadosModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(), SearchView.OnQueryTextListener {

    private var _binding: FragmentHomeBinding? = null
    private var db = Firebase.firestore
    private var listaCarrosel: MutableList<CarroselModel> = arrayListOf()

    val homeViewModel: HomeViewModel by viewModel()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        /*val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)*/

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        homeViewModel.listaCarrosel.observe(viewLifecycleOwner){ value ->
            var linearLayoutManager: LinearLayoutManager = LinearLayoutManager(requireContext())
            linearLayoutManager.orientation = RecyclerView.HORIZONTAL
            binding.recyclerViewCarrosel.layoutManager = linearLayoutManager
            binding.recyclerViewCarrosel.adapter = CarroselAdapter(value)
        }

        homeViewModel.listaAtividades.observe(viewLifecycleOwner){ value ->
            var linearLayoutManager2: LinearLayoutManager = LinearLayoutManager(requireContext())
            linearLayoutManager2.orientation = RecyclerView.HORIZONTAL
            binding.recyclerAtividades.layoutManager = linearLayoutManager2
            binding.recyclerAtividades.adapter = AtividadesAdapter(value)
        }

        homeViewModel.listaRecomendados.observe(viewLifecycleOwner){ value ->
            var linearLayoutManager3: LinearLayoutManager = LinearLayoutManager(requireContext())
            linearLayoutManager3.orientation = RecyclerView.HORIZONTAL
            binding.recyclerRecomendados.layoutManager = linearLayoutManager3
            binding.recyclerRecomendados.adapter = RecomendadosAdapter(value)
        }

        binding.cardTelaMontarRota.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_nav_home_to_montarRotasFragment)
        })

        binding.searchViewHome?.isSubmitButtonEnabled = true
        binding.searchViewHome?.setOnQueryTextListener(this)

        return root
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null){
            searchDataBase(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null){
            searchDataBase(query)
        }
        return true
    }

    private fun searchDataBase(query: String){
        val searchQuery = query

        listaCarrosel.clear()

        db.collection("lugares")
            .whereEqualTo("nomeLugar", searchQuery)
            .get()
            .addOnSuccessListener { result ->
                for (documents in result){
                    var note = documents.toObject(CarroselModel::class.java)

                    var p: CarroselModel = CarroselModel(
                        //imageLugar = note!!.imageLugar,
                        nomeLugar  = note!!.nomeLugar,
                        nomeLocalidade = note!!.nomeLocalidade
                    )
                    this.listaCarrosel.add(p)
                }
                binding.recyclerViewCarrosel.adapter?.notifyDataSetChanged()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}