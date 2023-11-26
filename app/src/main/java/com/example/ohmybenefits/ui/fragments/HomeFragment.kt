package com.example.ohmybenefits.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ohmybenefits.R
import com.example.ohmybenefits.adapters.CategoriaAdapter
import com.example.ohmybenefits.adapters.ProductoAdapter
import com.example.ohmybenefits.data.enums.Categorias
import com.example.ohmybenefits.data.model.CategoriaModel
import com.example.ohmybenefits.data.network.services.ProductoService
import com.example.ohmybenefits.ui.viewmodel.ProductoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    @Inject
    lateinit var productoService: ProductoService
    private lateinit var productoAdapter: ProductoAdapter
    private lateinit var categoriaAdapter: CategoriaAdapter
    private lateinit var productoRecyclerView: RecyclerView
    private lateinit var categoriasRecyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private val productoViewModel: ProductoViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchView = view.findViewById(R.id.searchView)
        productoRecyclerView = view.findViewById(R.id.recyclerView)
        categoriasRecyclerView = view.findViewById(R.id.catRecyclerView)
        val manager = GridLayoutManager(context, 2)
        productoRecyclerView.layoutManager = manager

        productoAdapter = ProductoAdapter(findNavController())
        productoRecyclerView.adapter = productoAdapter

        val categoriasLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        categoriasRecyclerView.layoutManager = categoriasLayoutManager
        categoriaAdapter = CategoriaAdapter(requireContext(), obtenerListaDeCategorias())
        categoriasRecyclerView.adapter = categoriaAdapter

        setListeners()
    }

    private fun obtenerListaDeCategorias(): List<CategoriaModel> {
        return listOf(
            CategoriaModel(Categorias.BEBIDAS, R.drawable.bebidas, 0),
            CategoriaModel(Categorias.LIMPIEZA, R.drawable.limpieza, 1),
            CategoriaModel(Categorias.PERFUMERIA, R.drawable.perfumeria, 2),
            CategoriaModel(Categorias.CONGELADOS, R.drawable.congelados, 3),
            CategoriaModel(Categorias.ALMACEN, R.drawable.almacen, 4)
        )
    }

    private fun setListeners() {

        productoRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState != RecyclerView.SCROLL_STATE_IDLE) {
                    return;
                }
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                val totalItemCount = layoutManager.itemCount
                if (lastVisibleItemPosition == totalItemCount - 1) {
                    productoViewModel.filtrarProductos(true)
                }
            }
        })

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(palabra: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                lifecycleScope.launch {
                    if (query != null) {
                        productoViewModel.clear()
                        productoViewModel.buscarPalabra(query)
                    }
                }
                return true
            }
        })

        categoriaAdapter.setOnItemClickListener(object : CategoriaAdapter.OnItemClickListener {
            override fun onItemClick(categoria: CategoriaModel, itemView: View) {
                lifecycleScope.launch {
                    productoViewModel.clear()
                    productoViewModel.setCategory(categoria.nombre.toString(), itemView as CardView)
                    productoViewModel.filtrarProductos(false)
                }
            }
        })

        lifecycleScope.launch {
            productoViewModel.listarProductos()
        }
        productoViewModel.productList.observe(viewLifecycleOwner) {
            productoAdapter.submitList(it)
        }
        productoViewModel.textError.observe(viewLifecycleOwner) {
            if (!it.isNullOrBlank()) {
                Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }
}
