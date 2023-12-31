package com.example.ohmybenefits.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ohmybenefits.R
import com.example.ohmybenefits.adapters.PresupuestoAdapter
import com.example.ohmybenefits.ui.viewmodel.PresupuestoViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.lifecycle.Observer
import com.example.ohmybenefits.ui.viewmodel.UsuarioViewModel

@AndroidEntryPoint
class PresupuestoFragment : Fragment() {
    private val presupuestoViewModel: PresupuestoViewModel by activityViewModels()
    private val adapter = PresupuestoAdapter()
    private val usuarioViewModel: UsuarioViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.presupuesto_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView? = view.findViewById(R.id.recyclerViewProductosSeleccionados)
        Log.d("PresupuestoFragment", "Contexto: $context")
        recyclerView?.layoutManager = LinearLayoutManager(context)
        recyclerView?.adapter = adapter
        presupuestoViewModel.productosSeleccionados.observe(viewLifecycleOwner, Observer { productos ->
            Log.d("PresupuestoFragment", "LiveData productosSeleccionados observado con productos: $productos")
            adapter.actualizarLista(productos)
        })
        presupuestoViewModel.presupuesto.observe(viewLifecycleOwner, Observer { total ->
            view?.findViewById<TextView>(R.id.textTotal)?.text = "Total: $$total"
        })
        val botonLimpiar = view.findViewById<Button>(R.id.botonLimpiarPresupuesto)
        botonLimpiar.setOnClickListener {
            adapter.limpiarLista()
            presupuestoViewModel.setPresupuestoVacio()
        }
        val botonGuardar = view.findViewById<Button>(R.id.botonGuardarPresupuesto)
        botonGuardar.setOnClickListener {
            presupuestoViewModel.guardarPresupuesto(usuarioViewModel)

            if (usuarioViewModel.mail.value == null) {
                Toast.makeText(
                    context,
                    "Para guardar su presupuesto primero debe iniciar sesión",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    context,
                    "Su presupuesto se ha guardado correctamente. Puede verlo desde nuestra página web.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}