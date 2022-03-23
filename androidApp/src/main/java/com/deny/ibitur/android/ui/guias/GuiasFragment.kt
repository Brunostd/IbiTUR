package com.deny.ibitur.android.ui.guias

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.deny.ibitur.android.R
import com.deny.ibitur.android.adapter.GuiaAdapter
import com.deny.ibitur.android.databinding.GuiasFragmentBinding
import com.deny.ibitur.android.model.AtividadesModel
import com.deny.ibitur.android.model.GuiaModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class GuiasFragment : Fragment() {

    private var _binding: GuiasFragmentBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var db: FirebaseFirestore = Firebase.firestore
    private var listaGuia: MutableList<GuiaModel> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = GuiasFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        var linearLayoutManager: LinearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        binding.recyclerGuia.layoutManager = linearLayoutManager
        binding.recyclerGuia.adapter = GuiaAdapter(listaGuia)

        return view
    }

    override fun onResume() {
        super.onResume()
        recuperarGuias()
    }

    override fun onStop() {
        super.onStop()
        listaGuia.clear()
    }

    fun recuperarGuias(){
        db.collection("guias")
            .get()
            .addOnSuccessListener { result ->
                for (documents in result){
                    var note = documents.toObject(GuiaModel::class.java)
                    this.listaGuia.add(note)
                }
                binding.recyclerGuia.adapter?.notifyDataSetChanged()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}