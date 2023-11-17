package com.example.ohmybenefits.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ohmybenefits.R
import com.example.ohmybenefits.adapters.RecomendedProductAdapter
import com.example.ohmybenefits.data.model.Recomendacion
import com.example.ohmybenefits.data.network.interfaces.OnItemClickListener
import com.example.ohmybenefits.databinding.FragmentDetalleBinding
import com.example.ohmybenefits.ui.viewmodel.DetalleViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetalleFragment : Fragment(), OnItemClickListener {
    private var _binding: FragmentDetalleBinding? = null
    private lateinit var detalleViewModel: DetalleViewModel
    private val binding get() = _binding!!
    val idUsuarioHardCodeado = "653eebee4162199cc1f81006"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetalleBinding.inflate(inflater, container, false)
        val view = binding.root
        detalleViewModel = ViewModelProvider(this).get(DetalleViewModel::class.java)
        val idProducto = arguments?.getString("idProducto", "-1") ?: "-1"
        val idUsuario = arguments?.getString("idUsuario", "-1") ?: "-1"
        detalleViewModel.cargarDetalleProducto(idProducto, idUsuario)

        detalleViewModel.detalleProducto.observe(viewLifecycleOwner, Observer { detalleProducto ->
            Log.d("DetalleFragment", "Detalle del Producto: ${detalleProducto.recomendaciones.recomms}")
            binding.textNombreProducto.text = detalleProducto.producto.nombre
            binding.textPrecioProducto.text = "$ ${detalleProducto.producto.precio}"
            Picasso.get()
                .load(detalleProducto.producto.imageUrl)
                .into(binding.imageProducto)

            val recyclerViewRecomendados: RecyclerView = view.findViewById(R.id.recyclerViewRecomendados)
            recyclerViewRecomendados.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            val adapter = RecomendedProductAdapter(detalleProducto, this, requireFragmentManager())
            recyclerViewRecomendados.adapter = adapter
        })
        detalleViewModel.errorMensaje.observe(viewLifecycleOwner, Observer { errorMensaje ->
            Log.e("DetalleFragment", "Error: $errorMensaje")
        })
        return view;
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    // Implementa la función onItemClick de la interfaz
    override fun onItemClick(recomendacion: Recomendacion) {
        // Aquí manejas el clic del usuario en el producto recomendado
        // Puedes, por ejemplo, iniciar una nueva transacción de fragmento para mostrar los detalles del producto recomendado
        val idProductoRecomendado = recomendacion.id
        val nuevoFragmento = DetalleFragment().apply {
            arguments = Bundle().apply {
                putString("idProducto", idProductoRecomendado)
                putString("idUsuario", idUsuarioHardCodeado) // Asegúrate de tener el idUsuario disponible
            }
        }

        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.contenedor_fragmento, nuevoFragmento)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}