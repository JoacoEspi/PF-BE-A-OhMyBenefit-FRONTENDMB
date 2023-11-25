package com.example.ohmybenefits.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ohmybenefits.R
import com.example.ohmybenefits.adapters.ProductoAdapter
import com.example.ohmybenefits.ui.viewmodel.ProductoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductoFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var productoAdapter: ProductoAdapter
    private val productoViewModel: ProductoViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("ProductoFragment", "onCreateView")

        productoViewModel.listarProductos()
        productoAdapter = ProductoAdapter(requireContext(), findNavController())

        return inflater.inflate(R.layout.producto_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val isLoading = view.findViewById<View>(R.id.loading)
        recyclerView = view.findViewById(R.id.products_rec)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = productoAdapter

        productoViewModel.productList.observe(viewLifecycleOwner) {
            productoAdapter.submitList(it)
        }

        productoViewModel.isLoading.observe(viewLifecycleOwner, Observer {
            isLoading.visibility = if (it) View.VISIBLE else View.GONE
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        productoViewModel.clear()
    }
}