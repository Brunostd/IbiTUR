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
import com.deny.ibitur.app.model.estabelecimentos.EstabelecimentosModel
import com.deny.ibitur.app.ui.fragments.cidades.CidadesFragmentDirections
import com.google.firebase.storage.FirebaseStorage

class Estabelecimentos2Adapter(var listaEstabelecimentos: MutableList<EstabelecimentosModel>):
    RecyclerView.Adapter<Estabelecimentos2Adapter.MyViewHolder>() {

    var storage: FirebaseStorage = FirebaseStorage.getInstance()

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(estabelecimentosModel: EstabelecimentosModel){

            var storageRef = storage.reference

            var cardAtividades: CardView = itemView.findViewById(R.id.cardAtividades)
            var imageEstabelecimentos: ImageView = itemView.findViewById(R.id.imageEstabelecimento)
            var nomeEstabelecimentos: TextView = itemView.findViewById(R.id.textNomeEstabelecimento)
            var cidadeEstabelecimentos: TextView = itemView.findViewById(R.id.textCidadeEstabelecimento)

            var spaceRef = storageRef.child("estabelecimentos/"+estabelecimentosModel.nomeEstabelecimento+".jpg")
            spaceRef.downloadUrl.addOnSuccessListener {
                val requestOptions = RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                Glide.with(itemView.context).load(it).apply(requestOptions).into(imageEstabelecimentos)
            }

            cardAtividades.setOnClickListener(View.OnClickListener {
                val action = CidadesFragmentDirections.actionCidadesFragmentToEstabelecimentoEscolhidoFragment(nomeEstabelecimentos.text.toString())
                itemView.findNavController().navigate(action)
            })

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