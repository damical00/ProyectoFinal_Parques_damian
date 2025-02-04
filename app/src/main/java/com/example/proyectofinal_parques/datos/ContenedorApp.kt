package com.example.proyectofinal_parques.datos

import android.content.Context
import com.example.proyectofinal_parques.conexion.ParquesBaseDatos
import com.example.proyectofinal_parques.conexion.ParquesServicioApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface ContenedorApp {
    val parquesRoomRepositorio: ParquesRoomRepositorio
    val parquesJSONRepositorio: ParquesJSONRepositorio
}

class ParqueContenedorApp(private val context: Context):ContenedorApp {

    private val baseUrl="http://192.168.0.32:8080/api/"
    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true //por si hay algun nulo
    }

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

        private val servicioRetrofit: ParquesServicioApi by lazy {
            retrofit.create(ParquesServicioApi::class.java)
        }

        override val parquesJSONRepositorio: ParquesJSONRepositorio by lazy {
            ConexionParquesJSONRepositorio(servicioRetrofit)
        }


    //------------PARTE PARA ROOM----------------
    override val parquesRoomRepositorio: ParquesRoomRepositorio by lazy{
        ConexionParquesRoomRepositorio(ParquesBaseDatos.obtenerBaseDatos(context).parquesDao())
    }
}