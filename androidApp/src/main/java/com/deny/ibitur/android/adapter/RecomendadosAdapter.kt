package com.deny.ibitur.android.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.deny.ibitur.android.R
import com.deny.ibitur.android.model.RecomendadosModel

class RecomendadosAdapter(var listaRecomendados: MutableList<RecomendadosModel>): RecyclerView.Adapter<RecomendadosAdapter.MyViewHolder>() {
    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(recomendadosModel: RecomendadosModel){

            var imageCidade: ImageView = itemView.findViewById(R.id.imageCidade)
            var nomeCidade: TextView = itemView.findViewById(R.id.nomeCidade)

            imageCidade.setImageResource(recomendadosModel.imageCidade)
            nomeCidade.text = recomendadosModel.nomeCidade
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recomendados, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(listaRecomendados[position])
    }

    override fun getItemCount(): Int = listaRecomendados.size
}