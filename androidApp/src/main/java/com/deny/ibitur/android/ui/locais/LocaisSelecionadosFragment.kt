package com.deny.ibitur.android.ui.locais

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.deny.ibitur.android.R
import com.deny.ibitur.android.databinding.LocaisSelecionadosFragmentBinding
import com.deny.ibitur.android.helper.Base64Custom
import com.deny.ibitur.android.model.CarroselModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class LocaisSelecionadosFragment : Fragment() {

    private var _binding: LocaisSelecionadosFragmentBinding? = null
    private val args: LocaisSelecionadosFragmentArgs by navArgs()
    private var base64Custom: Base64Custom = Base64Custom()
    private var db = Firebase.firestore
    private var storage: FirebaseStorage = FirebaseStorage.getInstance()
    private var mAtuh: FirebaseAuth = FirebaseAuth.getInstance()

    private lateinit var recebePreco: String
    private lateinit var recebeNomeLocalidade: String
    private lateinit var horarioFuncionamento: String
    private lateinit var nomeEstabelecimento: String
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = LocaisSelecionadosFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        var storageRef = storage.reference

        db.collection("lugares")
            .whereEqualTo("nomeLugar",args.recebeLocal)
            .get()
            .addOnSuccessListener { result ->
                for (documents in result){
                    var note = documents.toObject(CarroselModel::class.java)

                    var p: CarroselModel = CarroselModel(
                        //imageLugar = note!!.imageLugar,
                        nomeLugar  = note!!.nomeLugar,
                        nomeLocalidade = note!!.nomeLocalidade
                    )
                    binding.textNomeLocal.text = note!!.nomeLugar
                    //binding.imageLocal.setImageResource(note!!.imageLugar)
                    var spaceRef = storageRef.child("lugares/"+note!!.nomeLugar+".jpg")
                    spaceRef.downloadUrl.addOnSuccessListener {
                        Glide.with(this).load(it).into(binding.imageLocal)
                    }
                    recebePreco = note!!.precoLocal
                    recebeNomeLocalidade = note!!.nomeLocalidade
                    horarioFuncionamento = note!!.horarioFuncionamento
                    nomeEstabelecimento = note!!.nomeLugar
                    latitude = note!!.latitude
                    longitude = note!!.longitude
                    binding.textDescricaoLocal.text = note!!.descricaoLugar
                    binding.textEntradaLocal.text = note!!.entradaLocal
                    //this.listaCarrosel.add(p)
                }
                //binding.recyclerViewCarrosel.adapter?.notifyDataSetChanged()
            }

        var emailBase64 = base64Custom.codificarBase64(mAtuh.currentUser!!.email.toString())

        binding.textAdicionarItemLista.setOnClickListener(View.OnClickListener {
            val salvo = hashMapOf(
                "idEmail" to emailBase64,
                "nomeEstabelecimentoSalvo" to nomeEstabelecimento,
                "nomeLocalizacaoSalvo" to recebeNomeLocalidade,
                "horarioFuncionamentoSalvo" to horarioFuncionamento,
                "preco" to recebePreco,
                "tempoEstimado" to "--",
                "latitude" to latitude,
                "longitude" to longitude
            )

            db.collection("lugaresSalvos")
                .add(salvo)
                .addOnSuccessListener {
                    Toast.makeText(requireContext(), "Item salvo com sucesso no carrinho", Toast.LENGTH_LONG).show()
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), "Falha em salvar o item", Toast.LENGTH_LONG).show()
                }
        })

        return view
    }

    fun trazerLugar(){

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}