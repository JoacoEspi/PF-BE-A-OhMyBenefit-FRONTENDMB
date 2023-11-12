package com.example.ohmybenefits.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.ohmybenefits.R
import com.example.ohmybenefits.data.repositories.ProductoRepository
import com.example.ohmybenefits.ui.viewmodel.ProductoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var prodViewModel: ProductoViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prodViewModel = ViewModelProvider(this)[ProductoViewModel::class.java]

       // println(prodViewModel.getProd())
    }
}