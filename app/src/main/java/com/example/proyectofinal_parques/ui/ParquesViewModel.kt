package com.example.proyectofinal_parques.ui

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.proyectofinal_parques.ParquesAplicacion
import com.example.proyectofinal_parques.datos.ParquesRoomRepositorio
import com.example.proyectofinal_parques.modelo.ParquesRoom
import kotlinx.coroutines.launch

sealed class ParqueUIState {
    data class ObtenerExitoTodos(val listaParques: List<ParquesRoom>) : ParqueUIState()
    data class ObtenerExito(val parque: ParquesRoom) : ParqueUIState()
    data class EliminarExito(val parqueId: String) : ParqueUIState()
    object CrearExito : ParqueUIState()
    object ActualizarExito : ParqueUIState()
    object Error : ParqueUIState()
    object Cargando : ParqueUIState()
}

class ParquesViewModel(private val parquesRoomRepositorio: ParquesRoomRepositorio) : ViewModel() {

    var parqueUIState: ParqueUIState by mutableStateOf(ParqueUIState.Cargando)
        private set

    var parquePulsado =
        mutableStateOf(ParquesRoom(0, "", ""))  // Cambié ParquesJSON por ParquesRoom
        private set

    init {
        obtenerTodosParques()
    }

    // Método para obtener la lista de parques desde el repositorio
    fun obtenerTodosParques() {
        viewModelScope.launch {
            parqueUIState = try {
                val lista = parquesRoomRepositorio.obtenerTodosParques()
                Log.d("ParquesViewModel", "Parques obtenidos: $lista")
                ParqueUIState.ObtenerExitoTodos(lista)
            } catch (e: Exception) {
                Log.e("ParquesViewModel", "Error al obtener parques: ${e.message}")
                ParqueUIState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                // Obtén la instancia de la aplicación
                val aplicacion = (this[APPLICATION_KEY] as ParquesAplicacion)
                // Accede al repositorio de Room a través del contenedor
                val parquesRoomRepositorio = aplicacion.contenedor.parquesRoomRepositorio
                ParquesViewModel(parquesRoomRepositorio = parquesRoomRepositorio)
            }
        }
    }
}