package com.example.ohmybenefits.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ohmybenefits.adapters.ComentarioAdapter
import com.example.ohmybenefits.databinding.ComentarioFragmentBinding
import com.example.ohmybenefits.ui.viewmodel.ComentarioViewModel
import com.example.ohmybenefits.data.model.ComentarioModel
import android.widget.Toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ComentarioFragment : Fragment() {

    private lateinit var comentarioViewModel: ComentarioViewModel
    private var _binding: ComentarioFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var comentarioAdapter: ComentarioAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ComentarioFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        comentarioViewModel = ViewModelProvider(this).get(ComentarioViewModel::class.java)
        comentarioAdapter = ComentarioAdapter(mutableListOf())

        _binding?.apply {
            sendButton.setOnClickListener {
                val commentText = commentEditText?.text.toString()
                if (!commentText.isNullOrEmpty()) {
                    val commentModel = ComentarioModel(comentario = commentText)
                    comentarioViewModel.postComment(commentModel)

                    // Mostrar mensaje de agradecimiento usando Toast
                    showToast("Gracias por tu comentario")

                    // Limpiar el texto del comentario después de enviar
                    commentEditText.text.clear()
                } else {
                    // Manejar el caso cuando el texto del comentario es nulo o vacío
                    showToast("Por favor, ingresa un comentario antes de enviar")
                }
            }

            recyclerView.adapter = comentarioAdapter
            recyclerView.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            comentarioViewModel.getComentarios()
            comentarioViewModel.comentarioLiveData.observe(viewLifecycleOwner) { response ->
                Log.d("ComentarioFragment", "Callback ejecutado")
                if (response.isSuccessful) {
                    val comentarios = response.body()
                    val recyclerView: RecyclerView = binding.recyclerView
                    val layoutManager = LinearLayoutManager(context)
                    val adapter = ComentarioAdapter(response.body()!!)
                    recyclerView.layoutManager = layoutManager
                    recyclerView.adapter = adapter
                    comentarioAdapter.updateData(comentarios?: emptyList())
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("ComentarioFragment", "Error: $errorBody")
                }
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
