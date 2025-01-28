package com.example.proyectofinal_parques.modelo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ParquesRoom")
class ParquesRoom(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val nombre: String,
    val extension: String
) {
}