package com.example.proyectofinal_parques

import android.app.Application
import com.example.proyectofinal_parques.datos.ContenedorApp
import com.example.proyectofinal_parques.datos.ParqueContenedorApp

class ParquesAplicacion : Application(){
    lateinit var contenedor: ContenedorApp
    override fun onCreate() {
        super.onCreate()
        contenedor = ParqueContenedorApp(this)
    }
}