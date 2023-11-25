package com.example.ohmybenefits.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.ohmybenefits.R
import com.example.ohmybenefits.databinding.FragmentRestaurarContraseniaBinding
import com.example.ohmybenefits.ui.viewmodel.UsuarioViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RestaurarContraseniaFragment : Fragment() {

    private lateinit var binding: FragmentRestaurarContraseniaBinding
    private val usuarioViewModel: UsuarioViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRestaurarContraseniaBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()
        val spinner = binding.spinnerQuestions
        val emailInput = binding.resetPassEmailInput
        val respuestaInput =binding.resetPassAnsInput
        val contraseniaInput = binding.resetNewPassInput
        val btnReset = binding.resetPassButton

        if(spinner != null){
            ArrayAdapter.createFromResource(
                requireContext(),
                R.array.preguntas_seguridad,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
            }
        }


        btnReset.setOnClickListener {
            val email = emailInput.text.toString()
            val respuesta = respuestaInput.text.toString()
            val contrasenia = contraseniaInput.text.toString()
            var pregunta : String

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                   pregunta = parent?.getItemAtPosition(position).toString()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }

            pregunta = binding.spinnerQuestions.selectedItem.toString()

            if(email.isNotEmpty() && respuesta.isNotEmpty() && contrasenia.isNotEmpty() && pregunta != getString(R.string.registro_pregunta_subtittle)){
                try{
                    usuarioViewModel.resetearContrasenia(email, pregunta ,respuesta,contrasenia)
                    showSuccessDialog("Has podido restablecer tu contraseña, recuerda iniciar sesion")
                    Log.d("resultado reset:", usuarioViewModel.successMessage)

                    val action = RestaurarContraseniaFragmentDirections.actionRestaurarContraseniaFragmentToIniciarSesion()
                    navController.navigate(action)
                }catch(e: Exception){
                    showAlertDialog("Cuidado ${e.message.toString()}")
                }

            } else if(email.isEmpty()){
                showAlertDialog(getString(R.string.warning_reset_msg1))
            } else if(respuesta.isEmpty()){
                showAlertDialog(getString(R.string.warning_reset_msg2))
            } else if(contrasenia.isEmpty()){
                showAlertDialog(getString(R.string.warning_reset_msg3))
            } else if(pregunta == getString(R.string.registro_pregunta_subtittle)){
                showAlertDialog(getString(R.string.warning_reset_msg4))
            }



        }

    }

    private fun showAlertDialog(msg: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Hay un problema")
            .setMessage(msg)
            .setPositiveButton("Ok"){dialog, id ->
                Toast.makeText(requireContext(), "Eso es! sigue intentandolo", Toast.LENGTH_SHORT).show()
            }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showSuccessDialog(msg: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder
            .setMessage(msg)
            .setPositiveButton("Ok"){dialog, id ->
                Toast.makeText(requireContext(), "Eso es!", Toast.LENGTH_SHORT).show()
            }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

}