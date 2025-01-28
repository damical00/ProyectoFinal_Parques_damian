package com.example.proyectofinal_parques.conexion

import android.content.Context
import android.provider.CalendarContract.Instances
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.proyectofinal_parques.dao.ParqueDao
import com.example.proyectofinal_parques.modelo.ParquesRoom

@Database (entities = [ParquesRoom::class], version = 1, exportSchema = false)
abstract class ParquesBaseDatos : RoomDatabase() {

    abstract fun parquesDao(): ParqueDao

    companion object{
        @Volatile
        private var Instance: ParquesBaseDatos?=null

        fun obtenerBaseDatos(context: Context): ParquesBaseDatos{
            return Instance?: synchronized(this){
                Room.databaseBuilder(context, ParquesBaseDatos::class.java, "parquesdb")
                    .build()
                    .also { Instance=it }

            }
        }
    }
}