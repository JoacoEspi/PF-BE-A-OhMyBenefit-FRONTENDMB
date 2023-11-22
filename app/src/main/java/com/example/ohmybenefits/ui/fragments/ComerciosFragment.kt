package com.example.ohmybenefits.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ohmybenefits.adapters.ComercioAdapter
import com.example.ohmybenefits.ui.viewmodel.ComerciosViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.example.ohmybenefits.databinding.FragmentComerciosBinding

@AndroidEntryPoint
class ComerciosFragment : Fragment() {
    private val comerciosViewModel: ComerciosViewModel by viewModels()
    private var _binding: FragmentComerciosBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentComerciosBinding.inflate(inflater, container, false)

        //comerciosViewModel.obtenerComercios(-34.5270621941584, -58.49915655584191, 2, "")

        binding.btnObtenerUbicacion.setOnClickListener {
            // Lógica para obtener ubicación
            obtenerUbicacion()
        }

        binding.btnEnviar.setOnClickListener {
            // Lógica para enviar la dirección
            val direccion = binding.edtDireccion.text.toString()
            obtenerComercios(null, null, 2, direccion)
        }

        comerciosViewModel.comerciosResponse.observe(viewLifecycleOwner, Observer { response ->
            Log.d("ComerciosFragment", "Callback ejecutado")
            if (response.isSuccessful) {
                val recyclerView: RecyclerView = binding.recyclerViewComercio
                val layoutManager = LinearLayoutManager(context)
                val adapter = ComercioAdapter(response.body()!!) // Asegúrate de que la respuesta no sea nula

                recyclerView.layoutManager = layoutManager
                recyclerView.adapter = adapter
            } else {
                val errorBody = response.errorBody()?.string()
                Log.e("ComerciosFragment", "Error: $errorBody")
            }
        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun obtenerUbicacion() {
        // Lógica para obtener ubicación del dispositivo
        // ...
        val latitud = -34.5270621941584
        val longitud = -58.49915655584191
        // Luego, llama a obtenerComercios con las coordenadas obtenidas
        obtenerComercios(latitud, longitud, 2, "")
    }

    private fun obtenerComercios(latitud: Double?, longitud: Double?, codigoComercio: Int, direccion: String?) {
        comerciosViewModel.obtenerComercios(latitud, longitud, codigoComercio, direccion)
    }
}