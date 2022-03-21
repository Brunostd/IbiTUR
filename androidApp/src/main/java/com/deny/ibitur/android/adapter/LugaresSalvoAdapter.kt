package com.deny.ibitur.android.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.deny.ibitur.android.R
import com.deny.ibitur.android.model.LugaresSalvoModel
import com.deny.ibitur.android.ui.rotas.MontarRotasFragmentDirections
import com.google.firebase.storage.FirebaseStorage

class LugaresSalvoAdapter(var listaLugaresSalvos: MutableList<LugaresSalvoModel>):
    RecyclerView.Adapter<LugaresSalvoAdapter.MyViewHolder>() {
    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        var storage: FirebaseStorage = FirebaseStorage.getInstance()

        fun bind(lugaresSalvoModel: LugaresSalvoModel){

            var storageRef = storage.reference

            var cardLugaresSalvo: CardView = itemView.findViewById(R.id.cardLugaresSalvo)
            var imageLugaresSalvos: ImageView = itemView.findViewById(R.id.imageLugaresSalvos)
            var nomeEstabelecimentoSalvo: TextView = itemView.findViewById(R.id.nomeEstabelecimentoSalvo)
            var nomeLocalizacaoSalvo: TextView = itemView.findViewById(R.id.nomeLocalizacaoSalvo)
            var horarioFuncionamentoSalvo: TextView = itemView.findViewById(R.id.horarioFuncionamentoSalvo)
            var tempoEstimado: TextView = itemView.findViewById(R.id.tempoEstimado)
            var preco: TextView = itemView.findViewById(R.id.preco)

            var spaceRef = storageRef.child("lugares/"+lugaresSalvoModel.nomeEstabelecimentoSalvo+".jpg")

            spaceRef.downloadUrl.addOnSuccessListener {
                Glide.with(itemView.context).load(it).into(imageLugaresSalvos)
            }

            cardLugaresSalvo.setOnClickListener(View.OnClickListener {
                val action = MontarRotasFragmentDirections.actionMontarRotasFragmentToMapsFragment(lugaresSalvoModel.nomeEstabelecimentoSalvo)
                itemView.findNavController().navigate(action)
            })

            nomeEstabelecimentoSalvo.text = lugaresSalvoModel.nomeEstabelecimentoSalvo
            nomeLocalizacaoSalvo.text = lugaresSalvoModel.nomeLocalizacaoSalvo
            horarioFuncionamentoSalvo.text = lugaresSalvoModel.horarioFuncionamentoSalvo
            tempoEstimado.text = lugaresSalvoModel.tempoEstimado
            preco.text = lugaresSalvoModel.preco

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lugares_salvo, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(listaLugaresSalvos[position])
    }

    override fun getItemCount(): Int = listaLugaresSalvos.size
}