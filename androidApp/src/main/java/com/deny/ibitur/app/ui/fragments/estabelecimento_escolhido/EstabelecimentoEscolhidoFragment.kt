package com.deny.ibitur.app.ui.fragments.estabelecimento_escolhido

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.deny.ibitur.app.R
import com.deny.ibitur.app.databinding.EstabelecimentoEscolhidoFragmentBinding
import com.deny.ibitur.app.helper.Base64Custom
import com.deny.ibitur.app.model.estabelecimentos.EstabelecimentosModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class EstabelecimentoEscolhidoFragment : Fragment() {

    private var _binding: EstabelecimentoEscolhidoFragmentBinding? = null

    private val args: EstabelecimentoEscolhidoFragmentArgs by navArgs()
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
        _binding = EstabelecimentoEscolhidoFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        var storageRef = storage.reference

        db.collection("estabelecimentos")
            .whereEqualTo("nomeEstabelecimento",args.recebeEstabelecimento)
            .get()
            .addOnSuccessListener { result ->
                for (documents in result){
                    var note = documents.toObject(EstabelecimentosModel::class.java)

                    var p: EstabelecimentosModel = EstabelecimentosModel(
                        //imageLugar = note!!.imageLugar,
                        nomeEstabelecimento  = note!!.nomeEstabelecimento,
                        cidadeEstabelecimento = note!!.cidadeEstabelecimento
                    )
                    binding.textNomeLocal.text = note!!.nomeEstabelecimento
                    //binding.imageLocal.setImageResource(note!!.imageLugar)
                    var spaceRef = storageRef.child("estabelecimentos/"+note!!.nomeEstabelecimento+".jpg")
                    spaceRef.downloadUrl.addOnSuccessListener {
                        Glide.with(this).load(it).into(binding.imageLocal)
                    }
                    recebePreco = note!!.precoLocal
                    recebeNomeLocalidade = note!!.cidadeEstabelecimento
                    horarioFuncionamento = note!!.horarioFuncionamento
                    nomeEstabelecimento = note!!.nomeEstabelecimento
                    latitude = note!!.latitude
                    longitude = note!!.longitude
                    binding.textDescricaoLocal.text = note!!.descricaoTipoEstabelecimento
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

        binding.buttonMaisDetalhes.setOnClickListener(View.OnClickListener {
            maisDetalhes()
        })

        binding.buttonVerNoMapa.setOnClickListener(View.OnClickListener {
            val action = EstabelecimentoEscolhidoFragmentDirections.actionEstabelecimentoEscolhidoFragmentToMapsFragment(nomeEstabelecimento)
            findNavController().navigate(action)
        })

        binding.buttonMarcarVisita.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_estabelecimentoEscolhidoFragment_to_guiasFragment)
        })

        return view
    }

    fun maisDetalhes(){
        var layout = LayoutInflater.from(context).inflate(R.layout.dialog_mais_detalhes, null, false)

        /*var imagem: ImageView = layout.findViewById(R.id.imageViewMaisDetalhes)
        var detalhes: TextView = layout.findViewById(R.id.textViewMaisDetalhes)

        imagem.setImageDrawable(binding.imageLocal.drawable)
        detalhes.text = binding.textDescricaoLocal.text*/

        var alertDialog = MaterialAlertDialogBuilder(requireContext(),  R.style.ThemeOverlay_App_MaterialAlertDialog)
        alertDialog.setView(layout)
        alertDialog.setNegativeButton("Cancel", null)
        alertDialog.setPositiveButton("IR"){d, i->



        }.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}