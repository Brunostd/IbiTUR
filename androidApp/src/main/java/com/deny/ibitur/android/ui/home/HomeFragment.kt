package com.deny.ibitur.android.ui.home

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.SearchView
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
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment(), SearchView.OnQueryTextListener {

    private var _binding: FragmentHomeBinding? = null
    private var db = Firebase.firestore
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

            lugaresProximos()

        return root
    }

    override fun onPause() {
        super.onPause()
        listaCarrosel.clear()
    }

    /*fun addCarrosel(){
        var p: CarroselModel = CarroselModel(R.drawable.mirante_bosco, "Mirante do bosco", "Tiangua")
        listaCarrosel.add(p)
    }*/

    fun lugaresProximos(){
        db.collection("lugares")
            .get()
            .addOnSuccessListener { result ->
                for (documents in result){
                    var note = documents.toObject(CarroselModel::class.java)

                    var p: CarroselModel = CarroselModel(
                        imageLugar = note!!.imageLugar,
                        nomeLugar  = note!!.nomeLugar,
                        nomeLocalidade = note!!.nomeLocalidade
                    )
                    this.listaCarrosel.add(p)
                    var linearLayoutManager: LinearLayoutManager = LinearLayoutManager(requireContext())
                    linearLayoutManager.orientation = RecyclerView.HORIZONTAL
                    binding.recyclerViewCarrosel.layoutManager = linearLayoutManager
                    binding.recyclerViewCarrosel.adapter = CarroselAdapter(listaCarrosel)
                }
            }

        binding.searchViewHome?.isSubmitButtonEnabled = true
        binding.searchViewHome?.setOnQueryTextListener(this)
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
            .whereEqualTo("nomeLocalidade", searchQuery)
            .get()
            .addOnSuccessListener { result ->
                for (documents in result){
                    var note = documents.toObject(CarroselModel::class.java)

                    var p: CarroselModel = CarroselModel(
                        imageLugar = note!!.imageLugar,
                        nomeLugar  = note!!.nomeLugar,
                        nomeLocalidade = note!!.nomeLocalidade
                    )
                    this.listaCarrosel.add(p)
                }
                var linearLayoutManager: LinearLayoutManager = LinearLayoutManager(requireContext())
                linearLayoutManager.orientation = RecyclerView.HORIZONTAL
                binding.recyclerViewCarrosel.layoutManager = linearLayoutManager
                binding.recyclerViewCarrosel.adapter = CarroselAdapter(listaCarrosel)
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}