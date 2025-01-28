package com.example.proyectofinal_parques.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.proyectofinal_parques.datos.ParquesJSONRepositorio
import com.example.proyectofinal_parques.modelo.ParquesJSON

sealed interface ParqueUIState{
    data class ObtenerExitoTodos(val listaParques: List<ParquesJSON>) : ParqueUIState
    data class ObtenerExito (val parque : ParquesJSON) : ParqueUIState

    object CrearExito: ParqueUIState
    object ActualizarExito: ParqueUIState
    object Error : ParqueUIState
    object Cargando : ParqueUIState

    class ParquesViewModel (private val parquesJSONRepositorio: ParquesJSONRepositorio) : ViewModel(){
        var parqueUIState: ParqueUIState by mutableStateOf(ParqueUIState.Cargando)
            private set

        var parquePulsado: ParquesJSON by mutableStateOf(ParquesJSON("","","",""))
            private set
    }

}