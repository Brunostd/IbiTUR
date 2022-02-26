package com.deny.ibitur.android.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.deny.ibitur.android.R
import com.deny.ibitur.android.adapter.CarroselAdapter
import com.deny.ibitur.android.databinding.FragmentHomeBinding
import com.deny.ibitur.android.model.CarroselModel
import com.google.firebase.auth.FirebaseAuth

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private var listaCarrosel: MutableList<CarroselModel> = arrayListOf()


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        /*val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }*/

        addCarrosel()

        var linearLayoutManager: LinearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.orientation = RecyclerView.HORIZONTAL
        binding.recyclerViewCarrosel.layoutManager = linearLayoutManager
        binding.recyclerViewCarrosel.adapter = CarroselAdapter(listaCarrosel)

        return root
    }

    override fun onPause() {
        super.onPause()
        listaCarrosel.clear()
    }

    fun addCarrosel(){
        var p: CarroselModel = CarroselModel(R.drawable.fazenda, "Fazenda Gospel", "Ubajara")
        listaCarrosel.add(p)

        p = CarroselModel(R.drawable.fazenda, "Fazenda Gospel", "Ubajara")
        listaCarrosel.add(p)

        p = CarroselModel(R.drawable.fazenda, "Fazenda Gospel", "Ubajara")
        listaCarrosel.add(p)

        p = CarroselModel(R.drawable.fazenda, "Fazenda Gospel", "Ubajara")
        listaCarrosel.add(p)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}