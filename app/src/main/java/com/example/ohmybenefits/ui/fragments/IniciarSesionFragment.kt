package com.example.ohmybenefits.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ohmybenefits.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IniciarSesionFragment(): Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.iniciar_sesion_fragment, container, false)
    }

}
