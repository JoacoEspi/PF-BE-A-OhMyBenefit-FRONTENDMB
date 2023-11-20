package com.example.ohmybenefits.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ohmybenefits.R
import com.example.ohmybenefits.adapters.ProductoAdapter
import com.example.ohmybenefits.data.network.services.ProductoService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    @Inject
    lateinit var productoService: ProductoService
    private lateinit var productoAdapter: ProductoAdapter
    private lateinit var productoRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productoRecyclerView = view.findViewById(R.id.recyclerView)

        val productoLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        productoRecyclerView.layoutManager = productoLayoutManager

        val manager = GridLayoutManager(context, 2)
        productoRecyclerView.layoutManager = manager

        productoAdapter = ProductoAdapter(requireContext())
        productoRecyclerView.adapter = productoAdapter

        lifecycleScope.launch {
            val productos = productoService.listarProductos(1, 20)
            productoAdapter.setListaProducto(productos)
        }
    }
}
