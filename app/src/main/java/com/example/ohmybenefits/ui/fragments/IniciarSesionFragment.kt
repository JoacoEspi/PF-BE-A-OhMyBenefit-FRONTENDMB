package com.example.ohmybenefits.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.ohmybenefits.R
import com.example.ohmybenefits.databinding.IniciarSesionFragmentBinding
import com.example.ohmybenefits.ui.viewmodel.UsuarioViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class IniciarSesionFragment(): Fragment(){

    private lateinit var binding: IniciarSesionFragmentBinding
    private val usuarioViewModel: UsuarioViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = IniciarSesionFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState
        )
        val navController = findNavController()
        val emailInput = binding.emailAddressInput
        val contraseniaInput = binding.editTextPassword
        val loginBtn = binding.loginButton


       if(savedInstanceState == null) {
            navController.popBackStack(R.id.iniciarSesion, false)
        }

        val linkRecuperarCont = binding.forgotPassLink
        val linkRegistro = binding.registryLink

        linkRegistro.setOnClickListener{
            val action = IniciarSesionFragmentDirections.actionIniciarSesionToRegistroFragment()
            navController.navigate(action)
        }

        linkRecuperarCont.setOnClickListener{
            val action = IniciarSesionFragmentDirections.actionIniciarSesionToRestaurarContraseniaFragment()
            navController.navigate(action)

        }

        loginBtn.setOnClickListener{
            val email = emailInput.text.toString()
            val contrasenia = contraseniaInput.text.toString()

            if(email.isNotEmpty() && contrasenia.isNotEmpty()) {

                try{
                    usuarioViewModel.loginUsuario(email, contrasenia)
                    Log.d("Resultado desde fragment login",usuarioViewModel.successMessage)
                    val action = IniciarSesionFragmentDirections.actionIniciarSesionToHome()
                    navController.navigate(action)
                } catch (e: Exception){
                    showAlertDialog("Cuidado ${e.message.toString()}")
                }

            } else if(email.isEmpty()){
                showAlertDialog(getString(R.string.warning_email))
            } else if(contrasenia.isEmpty()){
                showAlertDialog(getString(R.string.warning_pass))
            }

        }


    }

    private fun showAlertDialog(message: String){
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Error")
            .setMessage(message)
            .setPositiveButton("Entendido"){ dialog, which ->
                Toast.makeText(requireContext(), "Eso es! sigue intentandolo!", Toast.LENGTH_SHORT).show()
            }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }


}

