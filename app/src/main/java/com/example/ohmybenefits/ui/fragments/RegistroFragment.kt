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
import com.example.ohmybenefits.databinding.FragmentRegistroBinding
import com.example.ohmybenefits.ui.viewmodel.UsuarioViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RegistroFragment : Fragment() {

    private lateinit var binding: FragmentRegistroBinding
    private val usuarioViewModel: UsuarioViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegistroBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()
        val registroBtn = binding.registroBoton
        val spinner = binding.registroSpinnerPreguntas
        val nombreInput = binding.registroNombreInput
        val apellidoInput = binding.registroApellidoInput
        val telefonoInput = binding.registroTelefonoInput
        val mailInput = binding.registroCorreoInput
        val fechaNacimientoInput = binding.registroFechaInput
        val contraseniaInput = binding.registroContraseniaInput
        val contConfirmacionInput = binding.registroContrasenia2Input
        val respuestaInput = binding.registroRespuestaInput

        if(spinner != null) {
            ArrayAdapter.createFromResource(
                requireContext(),
                R.array.preguntas_seguridad,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
            }
        }


        registroBtn.setOnClickListener{
            val nombre = nombreInput.text.toString()
            val apellido = apellidoInput.text.toString()
            val telefono = telefonoInput.text.toString()
            val mail = mailInput.text.toString()
            val fecha = fechaNacimientoInput.text.toString()
            val contrasenia = contraseniaInput.text.toString()
            val confirmacionCont = contConfirmacionInput.text.toString()
            val respuesta = respuestaInput.text.toString()


            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {

                   val pregunta = parent?.getItemAtPosition(position).toString()
                    Log.d("Resultado spinner registro", pregunta)

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }

            val preguntaInput = binding.registroSpinnerPreguntas.selectedItem.toString()
            if(preguntaInput == getString(R.string.registro_pregunta_subtittle)){
                showAlertDialog(getString(R.string.warning_registro_msg1))
            } else if (contrasenia != confirmacionCont){
                showAlertDialog(getString(R.string.warning_registro_msg2))
            } else if(nombre.isNullOrEmpty()){
                showAlertDialog(getString(R.string.warning_registro_msg3))
            } else if(apellido.isNullOrEmpty()){
                showAlertDialog(getString(R.string.warning_registro_msg4))
            } else if(telefono.isNullOrEmpty() || telefono.length > 10 || telefono.length < 10){
                showAlertDialog(getString(R.string.warning_registro_msg5))
            } else if(mail.isNullOrEmpty()){
                showAlertDialog(getString(R.string.warning_registro_msg6))
            } else if(fecha.isNullOrEmpty()) {
                showAlertDialog(getString(R.string.warning_registro_msg7))
            } else if(contrasenia.isNullOrEmpty()){
                showAlertDialog(getString(R.string.warning_registro_msg8))
            }else {
                try {
                    usuarioViewModel.registrarUsuario(nombre, apellido, telefono, mail, fecha, contrasenia, preguntaInput, respuesta)
                    Log.d("Resultado desde fragment registro", usuarioViewModel.successMessage)

                    showSuccessDialog("Usuario creado con exito, recuerda iniciar sesion nuevamente")

                    val action = RegistroFragmentDirections.actionRegistroFragmentToIniciarSesion()
                    navController.navigate(action)
                } catch(e: Exception){
                    showAlertDialog("Cuidado ${e.message.toString()}")
                }

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