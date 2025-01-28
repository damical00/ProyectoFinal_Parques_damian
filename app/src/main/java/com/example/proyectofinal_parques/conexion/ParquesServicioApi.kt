package com.example.proyectofinal_parques.conexion

import com.example.proyectofinal_parques.modelo.ParquesJSON
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ParquesServicioApi {

    @GET("parques")
    suspend fun obtenerParques(): List<ParquesJSON>

    @POST("parques")
    suspend fun insertarParques(
        @Body peliculas: ParquesJSON
    ):ParquesJSON

    @PUT("parques/{id}")
    suspend fun actualizarParques(
        @Path("id") id: String,
        @Body parquesJSON: ParquesJSON
    ):ParquesJSON

    @DELETE("parques/{id}")
    suspend fun eliminaParques(
        @Path("id") id:String
    ):ParquesJSON


}