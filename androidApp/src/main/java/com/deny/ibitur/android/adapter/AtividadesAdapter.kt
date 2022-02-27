package com.deny.ibitur.android.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.deny.ibitur.android.R
import com.deny.ibitur.android.model.AtividadesModel

class AtividadesAdapter(var listaAtividades: MutableList<AtividadesModel>): RecyclerView.Adapter<AtividadesAdapter.MyViewHolder>() {
    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(atividadesModel: AtividadesModel){

            var imageAtividades: ImageView = itemView.findViewById(R.id.imageAtividades)
            var tituloAtividades: TextView = itemView.findViewById(R.id.tituloAtividades)

            imageAtividades.setImageResource(atividadesModel.imageAtividade)
            tituloAtividades.text = atividadesModel.tituloAtividade
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.atividades, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(listaAtividades[position])
    }

    override fun getItemCount(): Int = listaAtividades.size
}