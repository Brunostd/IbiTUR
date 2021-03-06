package com.deny.ibitur.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.deny.ibitur.app.R
import com.deny.ibitur.app.model.recomendados.RecomendadosModel
import com.deny.ibitur.app.ui.fragments.home.HomeFragmentDirections
import com.google.firebase.storage.FirebaseStorage

class RecomendadosAdapter(var listaRecomendados: MutableList<RecomendadosModel>): RecyclerView.Adapter<RecomendadosAdapter.MyViewHolder>() {
    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        private var storage : FirebaseStorage = FirebaseStorage.getInstance()

        fun bind(recomendadosModel: RecomendadosModel){

            var storageRef = storage.reference

            var cardCidadeRecomendada: CardView = itemView.findViewById(R.id.cardCidadeRecomendada)
            var imageCidade: ImageView = itemView.findViewById(R.id.imageCidade)
            var nomeCidade: TextView = itemView.findViewById(R.id.nomeCidade)

            var spaceRef = storageRef.child("recomendados/"+recomendadosModel.nomeCidade+".jpg")

            spaceRef.downloadUrl.addOnSuccessListener {
                val requestOptions = RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                Glide.with(itemView.context).load(it).apply(requestOptions).into(imageCidade)
            }

            cardCidadeRecomendada.setOnClickListener(View.OnClickListener {
                val action = HomeFragmentDirections.actionNavHomeToCidadesFragment(recomendadosModel.nomeCidade)
                itemView.findNavController().navigate(action)
            })
            //imageCidade.setImageResource(recomendadosModel.imageCidade)
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