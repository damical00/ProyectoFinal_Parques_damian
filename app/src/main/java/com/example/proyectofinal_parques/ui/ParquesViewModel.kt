package com.example.proyectofinal_parques.ui

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

sealed interface ParqueUIState {
    data class ObtenerExitoTodos(val listaParques: List<ParquesRoom>) : ParqueUIState
    data class ObtenerExito(val parque: ParquesRoom) : ParqueUIState

    object CrearExito : ParqueUIState
    object ActualizarExito : ParqueUIState
    object Error : ParqueUIState
    object Cargando : ParqueUIState
}
    class ParquesViewModel (private val parquesRoomRepositorio: ParquesRoomRepositorio) : ViewModel(){
        var parqueUIState: ParqueUIState by mutableStateOf(ParqueUIState.Cargando)
            private set

        var parquePulsado: ParquesRoom by mutableStateOf(ParquesRoom(0,"",""))
            private set

        init {
            obtenerTodosParques()
        }

        // Obtiene la lista de parques desde el repositorio
        fun obtenerTodosParques() {
            viewModelScope.launch {
                parqueUIState = try {
                    val lista = parquesRoomRepositorio.obtenerTodosParques()
                    ParqueUIState.ObtenerExitoTodos(lista)
                } catch (e: Exception) {
                    ParqueUIState.Error // Manejo de error en la obtención de productos
                }
            }
        }

        // Obtiene un producto por su ID
        fun obtenerParque(id: Int) {
            viewModelScope.launch {
                parqueUIState = try {
                    val parque = parquesRoomRepositorio.obtenerParque(id)
                    parquePulsado = parque // Almacena el producto seleccionado
                    ParqueUIState.ObtenerExito(parque)
                } catch (e: Exception) {
                    ParqueUIState.Error // Manejo de error
                }
            }
        }

        // Inserta un nuevo parque en el repositorio
        fun insertarParque(parque: ParquesRoom){
            viewModelScope.launch {
                parqueUIState = try {
                    parquesRoomRepositorio.insertarParque(parque)
                    ParqueUIState.CrearExito
                }catch (e: Exception){
                    ParqueUIState.Error
                }
            }
        }

        // Actualiza un producto existente en el repositorio
        fun actualizarProducto(parque: ParquesRoom) {
            viewModelScope.launch {
                parqueUIState = try {
                    parquesRoomRepositorio.actualizarParques(parque)
                    ParqueUIState.ActualizarExito // Indica que la actualización fue exitosa
                } catch (e: Exception) {
                    ParqueUIState.Error // Manejo de error
                }
            }
        }

        // Factory para crear una instancia del ViewModel con su dependencia del repositorio
        companion object {
            val Factory: ViewModelProvider.Factory = viewModelFactory {
                initializer {
                    val aplicacion = (this[APPLICATION_KEY] as ParquesAplicacion)
                    val parquesRoomRepositorio = aplicacion.contenedor.parqueRoomRepositorio
                    ParquesViewModel(parquesRoomRepositorio = parquesRoomRepositorio)
                }
            }
        }
    }