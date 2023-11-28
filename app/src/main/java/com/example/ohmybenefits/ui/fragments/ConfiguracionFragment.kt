package com.example.ohmybenefits.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.ohmybenefits.R
import com.google.android.material.switchmaterial.SwitchMaterial
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConfiguracionFragment : Fragment(R.layout.configuracion_fragment) {

    private lateinit var switchDarkMode: SwitchMaterial
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        switchDarkMode = view.findViewById(R.id.swDarkMode)
        // Configuracion del Modo Oscuro
        switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Habilita el Modo Oscuro
                (requireActivity() as AppCompatActivity).delegate.localNightMode =
                    AppCompatDelegate.MODE_NIGHT_YES
            } else {
                // Deshabilita el Modo Oscuro
                (requireActivity() as AppCompatActivity).delegate.localNightMode =
                    AppCompatDelegate.MODE_NIGHT_NO
            }
        }
    }
}
