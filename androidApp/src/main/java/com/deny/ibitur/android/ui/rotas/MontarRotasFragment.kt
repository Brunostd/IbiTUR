package com.deny.ibitur.android.ui.rotas

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.deny.ibitur.android.R
import com.deny.ibitur.android.databinding.MontarRotasFragmentBinding
import com.deny.ibitur.android.model.LugaresSalvoModel

class MontarRotasFragment : Fragment() {

    private var _binding: MontarRotasFragmentBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var listaLugaresSalvo: MutableList<LugaresSalvoModel> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MontarRotasFragmentBinding.inflate(inflater, container, false)
        val view = binding.root



        return view
    }

    fun recuperarLugaresSalvos(){

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}