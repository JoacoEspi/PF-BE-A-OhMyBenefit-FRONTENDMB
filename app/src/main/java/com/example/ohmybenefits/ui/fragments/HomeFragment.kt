package com.example.ohmybenefits.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.ohmybenefits.R
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.home_fragment, container, false)

        val btnMostrarDetalle: Button = view.findViewById(R.id.btnMostrarDetalle)
        btnMostrarDetalle.setOnClickListener {
            mostrarDetalle(view)
        }

        return view
    }

    private fun mostrarDetalle(view: View) {
        val navController = view.findNavController()
        val direccion = HomeFragmentDirections.actionHomeToDetalleFragment("652df11a467785e35ecd79d5", "653eebee4162199cc1f81006")
        navController.navigate(direccion)
    }
}
