package com.example.ohmybenefits.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ohmybenefits.R
import com.example.ohmybenefits.adapters.RecomendedProductAdapter
import com.example.ohmybenefits.data.model.Recomendacion
import com.example.ohmybenefits.data.network.interfaces.OnItemClickListener
import com.example.ohmybenefits.databinding.FragmentDetalleBinding
import com.example.ohmybenefits.ui.viewmodel.DetalleViewModel
import com.example.ohmybenefits.ui.viewmodel.PresupuestoViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

@AndroidEntryPoint
class DetalleFragment : Fragment(), OnItemClickListener {
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentDetalleBinding? = null
    private lateinit var detalleViewModel: DetalleViewModel
    val presupuestoViewModel: PresupuestoViewModel by activityViewModels()
    private val binding get() = _binding!!
    val idUsuarioHardCodeado = "653eebee4162199cc1f81006"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDetalleBinding.inflate(inflater, container, false)

        val view = binding.root
        detalleViewModel = ViewModelProvider(this).get(DetalleViewModel::class.java)
        val idProducto = DetalleFragmentArgs.fromBundle(requireArguments()).producto
        val idUsuario = DetalleFragmentArgs.fromBundle((requireArguments())).usuario
        detalleViewModel.cargarDetalleProducto(idProducto, idUsuario)
        val botonComercio = binding.comerciosButton
        val botonPresupuesto = binding.presupuestoButton
        detalleViewModel.detalleProducto.observe(viewLifecycleOwner, Observer { detalleProducto ->
            Log.d("DetalleFragment", "Detalle del Producto: ${detalleProducto.recomendaciones.recomms}")
            binding.textNombreProducto.text = detalleProducto.producto.nombre
            binding.textPrecioProducto.text = "$ ${detalleProducto.producto.precio}"
            Picasso.get()
                .load(detalleProducto.producto.imageUrl)
                .into(binding.imageProducto)

            val recyclerViewRecomendados: RecyclerView = view.findViewById(R.id.recyclerViewRecomendados)
            recyclerViewRecomendados.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            val adapter = RecomendedProductAdapter(detalleProducto, this)
            recyclerViewRecomendados.adapter = adapter
        })
        detalleViewModel.errorMensaje.observe(viewLifecycleOwner, Observer { errorMensaje ->
            Log.e("DetalleFragment", "Error: $errorMensaje")
        })
        botonComercio.setOnClickListener {
            val navController = binding.root.findNavController()
            navController.navigate(DetalleFragmentDirections.actionDetalleFragmentToComerciosFragment())
        }
        botonPresupuesto.setOnClickListener {
            val cantidadText = binding.editCantidad.text.toString()
            if (cantidadText.isNotEmpty()) {
                val cantidad = cantidadText.toInt()
                detalleViewModel.detalleProducto.value?.let { detalleProducto ->
                    presupuestoViewModel.agregarProducto(detalleProducto.producto, cantidad)
                    Toast.makeText(
                        context,
                        "Producto agregado al presupuesto ${detalleProducto.producto.nombre} (Cantidad: $cantidad)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                // Maneja el caso cuando el EditText está vacío
                Toast.makeText(context, "Ingrese una cantidad válida", Toast.LENGTH_SHORT).show()
            }
        }
        return view;
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onItemClick(recomendacion: Recomendacion) {
        val navController = binding.root.findNavController()
        val idProductoRecomendado = recomendacion.id
        val direccion = DetalleFragmentDirections.actionDetalleFragmentSelf(idProductoRecomendado, idUsuarioHardCodeado)
        navController.navigate(direccion)
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DetalleFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
