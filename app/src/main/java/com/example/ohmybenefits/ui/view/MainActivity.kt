package com.example.ohmybenefits.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import com.example.ohmybenefits.R
import com.example.ohmybenefits.data.repositories.ProductoRepository
import com.example.ohmybenefits.ui.fragments.DetalleFragment
import com.example.ohmybenefits.ui.viewmodel.ProductoViewModel
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnMostrarDetalle: Button = findViewById(R.id.btnMostrarDetalle)
        btnMostrarDetalle.setOnClickListener { mostrarDetalleProducto("652df11a467785e35ecd79d5","653eebee4162199cc1f81006") }
    }
    private fun mostrarDetalleProducto(idProducto: String, idUsuario: String) {
        val detalleFragment = DetalleFragment()
        // Pasa los parámetros al fragmento utilizando un Bundle
        val bundle = Bundle()
        bundle.putString("idProducto", idProducto)
        bundle.putString("idUsuario", idUsuario)
        detalleFragment.arguments = bundle

        // Reemplaza el fragmento actual con el fragmento de detalle
        supportFragmentManager.beginTransaction()
            .replace(R.id.contenedor_fragmento, detalleFragment)
            .addToBackStack(null)
            .commit()
    }
}