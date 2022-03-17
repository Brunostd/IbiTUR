package com.deny.ibitur.android.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.deny.ibitur.android.R
import com.deny.ibitur.android.model.LugaresSalvoModel

class LugaresSalvoAdapter(var listaLugaresSalvos: MutableList<LugaresSalvoModel>):
    RecyclerView.Adapter<LugaresSalvoAdapter.MyViewHolder>() {
    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(lugaresSalvoModel: LugaresSalvoModel){

            var nomeEstabelecimentoSalvo: TextView = itemView.findViewById(R.id.nomeEstabelecimentoSalvo)
            var nomeLocalizacaoSalvo: TextView = itemView.findViewById(R.id.nomeLocalizacaoSalvo)
            var horarioFuncionamentoSalvo: TextView = itemView.findViewById(R.id.horarioFuncionamentoSalvo)
            var tempoEstimado: TextView = itemView.findViewById(R.id.tempoEstimado)
            var preco: TextView = itemView.findViewById(R.id.preco)

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