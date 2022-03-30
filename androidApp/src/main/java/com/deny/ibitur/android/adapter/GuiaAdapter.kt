package com.deny.ibitur.android.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.deny.ibitur.android.R
import com.deny.ibitur.android.model.GuiaModel
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

class GuiaAdapter(var listaGuia: MutableList<GuiaModel>):
    RecyclerView.Adapter<GuiaAdapter.MyViewHolder>() {
    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        var storage: FirebaseStorage = Firebase.storage

        fun bind(guiaModel: GuiaModel){

            var storageRef = storage.reference

            var imageGuia: ImageView = itemView.findViewById(R.id.imageViewGuia)
            var nomeGuia: TextView = itemView.findViewById(R.id.textViewNomeGuia)
            var cidadeGuia: TextView = itemView.findViewById(R.id.textViewCidadeGuia)
            var contatoGuia: TextView = itemView.findViewById(R.id.textViewContatoGuia)
            var precoGuia: TextView = itemView.findViewById(R.id.textViewPrecoGuia)

            var spaceRef = storageRef.child("guias/"+guiaModel.nomeGuia+".jpg")

            spaceRef.downloadUrl.addOnSuccessListener {
                val requestOptions = RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                Glide.with(itemView.context).load(it).apply(requestOptions).transform(CircleCrop()).into(imageGuia)
            }

            nomeGuia.text = guiaModel.nomeGuia
            cidadeGuia.text = guiaModel.cidadeGuia
            contatoGuia.text = guiaModel.contatoGuia
            precoGuia.text = guiaModel.precoGuia
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.guias, parent, false)
        return MyViewHolder(layout)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(listaGuia[position])
    }

    override fun getItemCount(): Int = listaGuia.size
}