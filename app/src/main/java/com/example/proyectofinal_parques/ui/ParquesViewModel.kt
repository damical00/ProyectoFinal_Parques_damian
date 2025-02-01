package com.example.proyectofinal_parques.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectofinal_parques.datos.ParquesRoomRepositorio
import com.example.proyectofinal_parques.datos.ParquesJSONRepositorio
import com.example.proyectofinal_parques.modelo.ParquesJSON
import com.example.proyectofinal_parques.modelo.ParquesRoom
import kotlinx.coroutines.launch
import java.io.IOException

sealed class ParqueUIState {
    // Estado cuando obtienes correctamente la lista de parques
    data class ObtenerExitoTodos(val listaParques: List<ParquesRoom>) : ParqueUIState()

    // Estado cuando obtienes un parque individual
    data class ObtenerExito(val parque: ParquesRoom) : ParqueUIState()

    // Estado cuando un parque fue eliminado correctamente
    data class EliminarExito(val parqueId: String) : ParqueUIState()

    // Estado cuando se creó un parque exitosamente
    object CrearExito : ParqueUIState()

    // Estado cuando un parque fue actualizado exitosamente
    object ActualizarExito : ParqueUIState()

    // Estado cuando ocurre un error
    object Error : ParqueUIState()

    // Estado cuando la operación está en curso (cargando)
    object Cargando : ParqueUIState()
}


class ParquesViewModel(private val parquesRoomRepositorio: ParquesRoomRepositorio) : ViewModel() {
    var parqueUIState: ParqueUIState by mutableStateOf(ParqueUIState.Cargando)
        private set

    var parquePulsado: ParquesJSON by mutableStateOf(ParquesJSON("","","",""))
        private set

    init {
        obtenerTodosParques()
    }

    // Método para obtener la lista de parques desde el repositorio
    fun obtenerTodosParques() {
        viewModelScope.launch {
            parqueUIState = try {
                val lista = parquesRoomRepositorio.obtenerTodosParques()
                ParqueUIState.ObtenerExitoTodos(lista)  // Pasamos la lista de parques
            } catch (e: Exception) {
                ParqueUIState.Error  // Si hay error, asignamos el estado Error
            }
        }
    }

    // Método para obtener un parque por su ID
    fun obtenerParque(id: Int) {
        viewModelScope.launch {
            parqueUIState = try {
                val parque = parquesRoomRepositorio.obtenerParque(id)
                parquePulsado = convertirRoomAParquesJSON(parque)  // Convertimos de Room a JSON
                ParqueUIState.ObtenerExito(parque)  // Pasamos el parque individual
            } catch (e: Exception) {
                ParqueUIState.Error  // Si hay error, asignamos el estado Error
            }
        }
    }

    // Método para insertar un parque en la base de datos
    fun insertarParque(parque: ParquesJSON) {
        viewModelScope.launch {
            parqueUIState = try {
                val parqueRoom = convertirJsonAParquesRoom(parque)  // Convertimos JSON a Room
                parquesRoomRepositorio.insertarParque(parqueRoom)  // Insertamos el parque
                ParqueUIState.CrearExito  // Estado de éxito
            } catch (e: Exception) {
                ParqueUIState.Error  // Si hay error, asignamos el estado Error
            }
        }
    }

    // Método para actualizar un parque en la base de datos
    fun actualizarParque(parque: ParquesJSON) {
        viewModelScope.launch {
            parqueUIState = try {
                val parqueRoom = convertirJsonAParquesRoom(parque)  // Convertimos JSON a Room
                parquesRoomRepositorio.actualizarParques(parqueRoom)  // Actualizamos el parque
                ParqueUIState.ActualizarExito  // Estado de éxito
            } catch (e: Exception) {
                ParqueUIState.Error  // Si hay error, asignamos el estado Error
            }
        }
    }

    // Método para eliminar un parque de la base de datos
    fun eliminarParque(id: Int) {
        viewModelScope.launch {
            parqueUIState = ParqueUIState.Cargando  // Estado de carga mientras se procesa
            try {
                parquesRoomRepositorio.eliminarParques(id)  // Eliminamos el parque
                parqueUIState = ParqueUIState.EliminarExito(id.toString())  // Estado de éxito con el id
            } catch (e: IOException) {
                parqueUIState = ParqueUIState.Error  // Si hay error, asignamos el estado Error
            }
        }
    }

    // Método para convertir de ParquesRoom a ParquesJSON
    private fun convertirRoomAParquesJSON(parquesRoom: ParquesRoom): ParquesJSON {
        return ParquesJSON(
            id = parquesRoom.id.toString(),  // Convertimos el id de Int a String
            nombre = parquesRoom.nombre,
            extension = parquesRoom.extension,
            especies = ""  // O lo que consideres necesario
        )
    }

    // Método para convertir de ParquesJSON a ParquesRoom
    private fun convertirJsonAParquesRoom(parquesJSON: ParquesJSON): ParquesRoom {
        return ParquesRoom(
            id = parquesJSON.id.toInt(),  // Convertimos el id de String a Int
            nombre = parquesJSON.nombre,
            extension = parquesJSON.extension
        )
    }
}
