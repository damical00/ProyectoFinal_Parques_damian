package com.example.proyectofinal_parques.datos

import android.content.Context
import kotlinx.serialization.json.Json

interface ContenedorApp {
    val parquesRoomRepositorio: ParquesRoomRepositorio
    val parquesJSONRepositorio: ParquesJSONRepositorio
}

class ParqueContenedorApp(private val context: Context):ContenedorApp {

    private val baseUrl="http://192.168.0.32:8080/api/parques"
    private val json = Json { ignoreUnknownKeys = true
    coerceInputValues = true //por si hay algun nulo
    }


}