package com.deny.ibitur.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.deny.ibitur.app.R
import com.deny.ibitur.app.model.estabelecimentos.EstabelecimentosModel
import com.deny.ibitur.app.ui.fragments.restaurantes.RestaurantesFragmentDirections
//import com.deny.ibitur.android.R
import com.google.firebase.storage.FirebaseStorage

class ExploreLocaisAdapter(var listaExploreLocais: MutableList<EstabelecimentosModel>): RecyclerView.Adapter<ExploreLocaisAdapter.MyViewHolder>() {
    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        var storage: FirebaseStorage = FirebaseStorage.getInstance()

        fun bind(exploreLocaisModel: EstabelecimentosModel){

            var storageRef = storage.reference

            var imageExploreLocais: ImageView = itemView.findViewById(R.id.imageExplore)
            var nomeExploreLocais: TextView = itemView.findViewById(R.id.nomeExplore)
            var cidadeExploreLocais: TextView = itemView.findViewById(R.id.cidadeExplore)
            var buttonExploreLocais: Button = itemView.findViewById(R.id.buttonExploreLocais)

            var spaceRef = storageRef.child("estabelecimentos/"+exploreLocaisModel.nomeEstabelecimento+".jpg")

            spaceRef.downloadUrl.addOnSuccessListener {
                val requestOptions = RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                Glide.with(itemView.context).load(it).apply(requestOptions).transform(CircleCrop()).into(imageExploreLocais)
            }

            buttonExploreLocais.setOnClickListener(View.OnClickListener {
                val action = RestaurantesFragmentDirections.actionRestaurantesFragmentToEstabelecimentoEscolhidoFragment(nomeExploreLocais.text.toString())
                itemView.findNavController().navigate(action)
            })

            nomeExploreLocais.text = exploreLocaisModel.nomeEstabelecimento
            cidadeExploreLocais.text = exploreLocaisModel.cidadeEstabelecimento
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.explore_locais, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(listaExploreLocais[position])
    }

    override fun getItemCount(): Int = listaExploreLocais.size
}