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
import com.deny.ibitur.android.model.CarroselModel
import com.deny.ibitur.android.ui.cidades.CidadesFragmentDirections
import com.deny.ibitur.android.ui.home.HomeFragmentDirections
import com.google.firebase.storage.FirebaseStorage

class Carrosel2Adapter(var listaCarrosel: MutableList<CarroselModel>): RecyclerView.Adapter<Carrosel2Adapter.MyViewHolder>() {
    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        var storage: FirebaseStorage = FirebaseStorage.getInstance()

        fun bind(carroselModel: CarroselModel){
            var storageRef = storage.reference

            var cardCarrosel: CardView = itemView.findViewById(R.id.cardCarrosel)
            var imageCarrosel: ImageView = itemView.findViewById(R.id.imageViewCarrosel)
            var textLugaresCarrosel: TextView = itemView.findViewById(R.id.textLugaresCarrosel)
            var textLocalidadeCarrosel: TextView = itemView.findViewById(R.id.textLocalidadeCarrosel)

            var spaceRef = storageRef.child("lugares/"+carroselModel.nomeLugar+".jpg")

            spaceRef.downloadUrl.addOnSuccessListener {
                Glide.with(itemView.context).load(it).into(imageCarrosel)
            }

            //imageCarrosel.setImageResource(carroselModel.imageLugar)
            textLugaresCarrosel.text = carroselModel.nomeLugar
            textLocalidadeCarrosel.text = carroselModel.nomeLocalidade

            cardCarrosel.setOnClickListener(View.OnClickListener {
                val action = CidadesFragmentDirections.actionCidadesFragmentToLocaisSelecionadosFragment(textLugaresCarrosel.text.toString())
                itemView.findNavController().navigate(action)
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.carosel_lugares, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(listaCarrosel[position])
    }

    override fun getItemCount(): Int = listaCarrosel.size
}