package com.example.proyectofinal_parques.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.proyectofinal_parques.modelo.ParquesRoom

@Dao
interface ParqueDao {

    @Query("SELECT * FROM ParquesRoom WHERE id= :id")
    suspend fun obtenerParque(id: Int):ParquesRoom

    @Query("SELECT * FROM ParquesRoom ORDER BY nombre ASC")
    suspend fun obtenerTodosParques(): List<ParquesRoom>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertarParque(parquesRoom: ParquesRoom)

    @Update
    suspend fun actualizarParque(parquesRoom: ParquesRoom)

    @Delete
    suspend fun eliminarParque(parquesRoom: Int)

}


