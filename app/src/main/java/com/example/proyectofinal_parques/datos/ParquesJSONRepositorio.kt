package com.example.proyectofinal_parques.datos

import com.example.proyectofinal_parques.conexion.ParquesServicioApi
import com.example.proyectofinal_parques.modelo.ParquesJSON

interface ParquesJSONRepositorio{
    suspend fun obtenerParque(): List<ParquesJSON>
    suspend fun insertarParque(parquesJSON: ParquesJSON): ParquesJSON
    suspend fun actualizarParque(id: String,parquesJSON: ParquesJSON):ParquesJSON
    suspend fun eliminarParque (id:String):ParquesJSON
}

class ConexionParquesJSONRepositorio(
    private val parquesServicioApi: ParquesServicioApi
):ParquesJSONRepositorio{
    override suspend fun obtenerParque(): List<ParquesJSON> =parquesServicioApi.obtenerParques()
    override suspend fun insertarParque(parquesJSON: ParquesJSON): ParquesJSON = parquesServicioApi.insertarParques(parquesJSON)
    override suspend fun actualizarParque(id: String, parquesJSON: ParquesJSON): ParquesJSON = parquesServicioApi.actualizarParques(id, parquesJSON)
    override suspend fun eliminarParque(id: String): ParquesJSON = parquesServicioApi.eliminaParques(id)
}