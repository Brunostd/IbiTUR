package com.deny.ibitur.android.ui.home

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListAdapter
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.deny.ibitur.android.R
import com.deny.ibitur.android.adapter.AtividadesAdapter
import com.deny.ibitur.android.adapter.CarroselAdapter
import com.deny.ibitur.android.databinding.FragmentHomeBinding
import com.deny.ibitur.android.model.AtividadesModel
import com.deny.ibitur.android.model.CarroselModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment(), SearchView.OnQueryTextListener {

    private var _binding: FragmentHomeBinding? = null
    private var db = Firebase.firestore
    private var listaCarrosel: MutableList<CarroselModel> = arrayListOf()
    private var listaAtividades: MutableList<AtividadesModel> = arrayListOf()


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

        var linearLayoutManager: LinearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.orientation = RecyclerView.HORIZONTAL
        binding.recyclerViewCarrosel.layoutManager = linearLayoutManager
        binding.recyclerViewCarrosel.adapter = CarroselAdapter(listaCarrosel)

        var linearLayoutManager2: LinearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager2.orientation = RecyclerView.HORIZONTAL
        binding.recyclerAtividades.layoutManager = linearLayoutManager2
        binding.recyclerAtividades.adapter = AtividadesAdapter(listaAtividades)


        binding.searchViewHome?.isSubmitButtonEnabled = true
        binding.searchViewHome?.setOnQueryTextListener(this)

        return root
    }

    override fun onResume() {
        super.onResume()
        lugaresProximos()
        atividades()
    }

    override fun onPause() {
        super.onPause()
        listaCarrosel.clear()
        listaAtividades.clear()
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
                }
                binding.recyclerViewCarrosel.adapter?.notifyDataSetChanged()
            }
    }

    fun atividades(){

        /*var p: AtividadesModel = AtividadesModel(R.drawable.image_phistorico, "P.Historicos")
        listaAtividades.add(p)

        db.collection("atividades")
            .add(listaAtividades[0])
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot written with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }*/

        db.collection("atividades")
            .get()
            .addOnSuccessListener { result ->
                for (documents in result){
                    var note = documents.toObject(AtividadesModel::class.java)

                    var n: AtividadesModel = AtividadesModel(
                        imageAtividade = note!!.imageAtividade,
                        tituloAtividade = note!!.tituloAtividade
                    )
                    this.listaAtividades.add(n)
                }
                binding.recyclerAtividades.adapter?.notifyDataSetChanged()
            }
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
                binding.recyclerViewCarrosel.adapter?.notifyDataSetChanged()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}