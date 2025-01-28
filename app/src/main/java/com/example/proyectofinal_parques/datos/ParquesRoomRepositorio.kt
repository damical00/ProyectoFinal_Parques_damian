package com.example.proyectofinal_parques.datos

import com.example.proyectofinal_parques.conexion.ParquesServicioApi
import com.example.proyectofinal_parques.dao.ParqueDao
import com.example.proyectofinal_parques.modelo.ParquesRoom

interface ParquesRoomRepositorio{
    suspend fun obtenerParque(id: Int): ParquesRoom
    suspend fun obtenerTodosParques(): List<ParquesRoom>
    suspend fun insertarParque(parquesRoom: ParquesRoom)
    suspend fun actualizarParques(parquesRoom: ParquesRoom)
    suspend fun eliminarParques(parquesRoom: ParquesRoom)
}

class ConexionParquesRoomRepositorio(
    private val parqueDao: ParqueDao
):ParquesRoomRepositorio{
    override suspend fun obtenerParque(id: Int): ParquesRoom = parqueDao.obtenerParque(id)
    override suspend fun obtenerTodosParques(): List<ParquesRoom> = parqueDao.obtenerTodosParques()
    override suspend fun insertarParque(parquesRoom: ParquesRoom) = parqueDao.insertarParque(parquesRoom)
    override suspend fun actualizarParques(parquesRoom: ParquesRoom) = parqueDao.actualizarParque(parquesRoom)
    override suspend fun eliminarParques(parquesRoom: ParquesRoom) = parqueDao.eliminarParque(parquesRoom)
}