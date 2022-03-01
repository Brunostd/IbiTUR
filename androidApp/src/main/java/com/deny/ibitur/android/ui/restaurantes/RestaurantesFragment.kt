package com.deny.ibitur.android.ui.restaurantes

import android.content.ContentValues.TAG
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.deny.ibitur.android.R
import com.deny.ibitur.android.databinding.RestaurantesFragmentBinding
import com.deny.ibitur.android.model.EstabelecimentosModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RestaurantesFragment : Fragment() {

    private var _binding: RestaurantesFragmentBinding? = null
    private val args: RestaurantesFragmentArgs by navArgs()
    private var db = Firebase.firestore
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = RestaurantesFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

       /* var p: EstabelecimentosModel = EstabelecimentosModel(R.drawable.casa_do_baiao, "Casa do baião", "São Benedito")

        db.collection("estabelecimentos")
            .add(p)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot written with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }*/

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}