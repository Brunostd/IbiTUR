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
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.deny.ibitur.android.R
import com.deny.ibitur.android.model.AtividadesModel
import com.deny.ibitur.android.ui.home.HomeFragmentDirections
import com.google.firebase.storage.FirebaseStorage

class AtividadesAdapter(var listaAtividades: MutableList<AtividadesModel>): RecyclerView.Adapter<AtividadesAdapter.MyViewHolder>() {
    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        private var storage: FirebaseStorage = FirebaseStorage.getInstance()

        fun bind(atividadesModel: AtividadesModel){

            var storageRef = storage.reference

            var cardAtividades: CardView = itemView.findViewById(R.id.cardAtividades)
            var imageAtividades: ImageView = itemView.findViewById(R.id.imageAtividades)
            var tituloAtividades: TextView = itemView.findViewById(R.id.tituloAtividades)

            var spaceRef = storageRef.child("atividades/"+atividadesModel.tituloAtividade+".png")

            spaceRef.downloadUrl.addOnSuccessListener {
                val requestOptions = RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                Glide.with(itemView.context).load(it).apply(requestOptions).into(imageAtividades)
            }

            //imageAtividades.setImageResource(atividadesModel.imageAtividade)
            tituloAtividades.text = atividadesModel.tituloAtividade

            cardAtividades.setOnClickListener(View.OnClickListener {
                val action = HomeFragmentDirections.actionNavHomeToRestaurantesFragment(tituloAtividades.text.toString())
                itemView.findNavController().navigate(action)
            })
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