package com.deny.ibitur.android.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.deny.ibitur.android.R
import com.deny.ibitur.android.model.EstabelecimentosModel

class EstabelecimentosAdapter(var listaEstabelecimentos: MutableList<EstabelecimentosModel>):
    RecyclerView.Adapter<EstabelecimentosAdapter.MyViewHolder>() {
    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(estabelecimentosModel: EstabelecimentosModel){

            var imageEstabelecimentos: ImageView = itemView.findViewById(R.id.imageEstabelecimento)
            var nomeEstabelecimentos: TextView = itemView.findViewById(R.id.textNomeEstabelecimento)
            var cidadeEstabelecimentos: TextView = itemView.findViewById(R.id.textCidadeEstabelecimento)

            //imageEstabelecimentos.setImageResource(estabelecimentosModel.imageEstabelecimento)
            nomeEstabelecimentos.text = estabelecimentosModel.nomeEstabelecimento
            cidadeEstabelecimentos.text = estabelecimentosModel.cidadeEstabelecimento
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.atividades_recomendadas, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(listaEstabelecimentos[position])
    }

    override fun getItemCount(): Int = listaEstabelecimentos.size
}