package com.deny.ibitur.app.ui.fragments.rotas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.deny.ibitur.app.adapter.LugaresSalvoAdapter
import com.deny.ibitur.app.databinding.MontarRotasFragmentBinding
import com.deny.ibitur.app.helper.Base64Custom
import com.deny.ibitur.app.model.lugares.LugaresSalvoModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MontarRotasFragment : Fragment() {

    private var _binding: MontarRotasFragmentBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var db: FirebaseFirestore = Firebase.firestore
    private var base64Custom: Base64Custom = Base64Custom()
    private var listaLugaresSalvo: MutableList<LugaresSalvoModel> = arrayListOf()
    var auth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MontarRotasFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        var linearLayoutManager: LinearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        binding.recyclerLugaresSalvo.layoutManager = linearLayoutManager
        binding.recyclerLugaresSalvo.adapter = LugaresSalvoAdapter(listaLugaresSalvo)

        return view
    }

    override fun onResume() {
        super.onResume()
        recuperarLugaresSalvos()
    }

    fun recuperarLugaresSalvos(){

        var idEmail = base64Custom.codificarBase64(auth.currentUser!!.email.toString())

        db.collection("lugaresSalvos")
            .whereEqualTo("idEmail",idEmail)
            .get()
            .addOnSuccessListener { result ->
                for (documents in result){
                    var note = documents.toObject(LugaresSalvoModel::class.java)

                    listaLugaresSalvo.add(note)
                }
                Toast.makeText(requireContext(), "Sucesso", Toast.LENGTH_LONG).show()
                binding.recyclerLugaresSalvo.adapter?.notifyDataSetChanged()
            }
    }

    override fun onPause() {
        super.onPause()
        listaLugaresSalvo.clear()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}