package com.deny.ibitur.app.ui.activities

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.deny.ibitur.app.R
import com.deny.ibitur.app.model.lugares.LugaresSalvoModel
//import com.deny.ibitur.android.model.RotaSalvaModel

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MapsFragment : Fragment() {

    var db = Firebase.firestore
    private val args: MapsFragmentArgs by navArgs()
    //lateinit var rotaSalva: RotaSalvaModel


    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */

        db.collection("lugaresSalvos")
            .whereEqualTo("nomeEstabelecimentoSalvo", args.nomeEstabelecimento)
            .get()
            .addOnSuccessListener { result ->
                for (documents in result){
                    var note = documents.toObject(LugaresSalvoModel::class.java)

                    var latLng: LatLng = LatLng(note!!.latitude, note!!.longitude)

                    val sydney = latLng
                    googleMap.addMarker(MarkerOptions().position(sydney).title(note!!.nomeEstabelecimentoSalvo))
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

}