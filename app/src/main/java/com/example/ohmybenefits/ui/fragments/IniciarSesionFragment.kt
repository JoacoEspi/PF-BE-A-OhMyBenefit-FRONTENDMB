package com.example.ohmybenefits.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.ohmybenefits.R
import com.example.ohmybenefits.databinding.IniciarSesionFragmentBinding
import com.example.ohmybenefits.ui.viewmodel.UsuarioViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class IniciarSesionFragment(): Fragment(){

    private lateinit var binding: IniciarSesionFragmentBinding
    private val usuarioViewModel: UsuarioViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = IniciarSesionFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.forgotPassLink.setOnClickListener {
            findNavController().navigate(R.id.action_iniciarSesion_to_restaurarContraseniaFragment)
        }

        binding.registryLink.setOnClickListener {
            findNavController().navigate(R.id.action_iniciarSesion_to_registroFragment)
        }


    }



}

