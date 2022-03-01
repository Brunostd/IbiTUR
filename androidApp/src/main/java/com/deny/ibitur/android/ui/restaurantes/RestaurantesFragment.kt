package com.deny.ibitur.android.ui.restaurantes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.deny.ibitur.android.adapter.EstabelecimentosAdapter
import com.deny.ibitur.android.databinding.RestaurantesFragmentBinding
import com.deny.ibitur.android.model.EstabelecimentosModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class RestaurantesFragment : Fragment() {

    private var _binding: RestaurantesFragmentBinding? = null
    private val args: RestaurantesFragmentArgs by navArgs()
    private var listaEstabelecimentos: MutableList<EstabelecimentosModel> = arrayListOf()
    private var db = Firebase.firestore
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



        var linearLayoutManager: LinearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.orientation = RecyclerView.HORIZONTAL
        binding.recyclerEstabelecimentos.layoutManager = linearLayoutManager
        binding.recyclerEstabelecimentos.adapter = EstabelecimentosAdapter(listaEstabelecimentos)

        return view
    }

    override fun onResume() {
        super.onResume()
        estabelecimentoRestaurantes()
        estabelecimentoEcoTurismo()
    }

    override fun onPause() {
        super.onPause()
        listaEstabelecimentos.clear()
    }

    fun estabelecimentoEcoTurismo(){

        if(args.recebeAtividade.equals("Eco-Turismo")){
            var spaceRef = storageRef.child("atividades/"+args.recebeAtividade+".png")
            spaceRef.downloadUrl.addOnSuccessListener {
                Glide.with(this).load(it).into(binding.iconeAtividades)
            }

            db.collection("estabelecimentos")
                .whereEqualTo("tipoEstabelecimento", args.recebeAtividade)
                .get()
                .addOnSuccessListener { result ->
                    for (documents in result){
                        var note = documents.toObject(EstabelecimentosModel::class.java)

                        var p: EstabelecimentosModel = EstabelecimentosModel(
                            nomeEstabelecimento = note!!.nomeEstabelecimento,
                            cidadeEstabelecimento = note!!.cidadeEstabelecimento
                        )
                        this.listaEstabelecimentos.add(p)
                        binding.descricaoTipoEstabelecimento.text = note!!.descricaoTipoEstabelecimento
                        binding.tipoEstabelecimento.text = note!!.tipoEstabelecimento
                    }
                    binding.recyclerEstabelecimentos.adapter?.notifyDataSetChanged()
                }

        }
    }

    fun estabelecimentoRestaurantes(){

        if(args.recebeAtividade.equals("Restaurantes")){
            var spaceRef = storageRef.child("atividades/"+args.recebeAtividade+".png")
            spaceRef.downloadUrl.addOnSuccessListener {
                Glide.with(this).load(it).into(binding.iconeAtividades)
            }

            db.collection("estabelecimentos")
                .whereEqualTo("tipoEstabelecimento", args.recebeAtividade)
                .get()
                .addOnSuccessListener { result ->
                    for (documents in result){
                        var note = documents.toObject(EstabelecimentosModel::class.java)

                        var p: EstabelecimentosModel = EstabelecimentosModel(
                            nomeEstabelecimento = note!!.nomeEstabelecimento,
                            cidadeEstabelecimento = note!!.cidadeEstabelecimento
                        )
                        this.listaEstabelecimentos.add(p)
                        binding.descricaoTipoEstabelecimento.text = note!!.descricaoTipoEstabelecimento
                        binding.tipoEstabelecimento.text = note!!.tipoEstabelecimento
                    }
                    binding.recyclerEstabelecimentos.adapter?.notifyDataSetChanged()
                }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}