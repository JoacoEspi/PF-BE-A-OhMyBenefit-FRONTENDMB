package com.example.ohmybenefits.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ohmybenefits.R
import com.example.ohmybenefits.adapters.ProductoAdapter
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
    private lateinit var productoRecyclerView: RecyclerView
    private val productoViewModel: ProductoViewModel by activityViewModels()

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
            productoViewModel.loadNextPage()
        }

        productoViewModel.productList.observe(viewLifecycleOwner) {
            productoAdapter.submitList(it)
        }

        productoRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                val totalItemCount = layoutManager.itemCount

                if (lastVisibleItemPosition == totalItemCount - 1) {
                    productoViewModel.loadNextPage()
                }
            }
        })
    }
}
