package com.example.proyectofinal_parques.ui.Pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyectofinal_parques.R
import com.example.proyectofinal_parques.modelo.ParquesRoom
import com.example.proyectofinal_parques.ui.ParqueUIState
import com.example.proyectofinal_parques.ui.ParquesViewModel

@Composable
fun PantallaJson(
    viewModel: ParquesViewModel = viewModel()  // Inicializa el ViewModel
) {
    // Accede al estado del ViewModel
    val uiState = viewModel.parqueUIState

    // Ahora puedes usar el estado uiState para manejar la UI, como mostrar los parques
    when (uiState) {
        is ParqueUIState.Cargando -> {
            // Mostrar estado de cargando
            CircularProgressIndicator()
        }
        is ParqueUIState.Error -> {
            // Mostrar un mensaje de error
            Text(text = "Error al cargar los datos")
        }
        is ParqueUIState.ObtenerExitoTodos -> {
            // Aquí puedes acceder a la lista de parques
            LazyColumn {
                items(uiState.listaParques) { parque ->
                    Text(text = parque.nombre)
                }
            }
        }
        else -> {
            // Maneja otros estados si los tienes
        }
    }
}


@Composable
fun PantallaCargando(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.cargando),
        contentDescription = "Cargando"
    )
}

@Composable
fun PantallaError(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.error),
        contentDescription = "Error"
    )
}

@Composable
fun PantallaListaParquesJson(
    lista: List<ParquesRoom>,
    onParquePulsado: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(lista) { parque ->
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .clickable(
                        onClick = { onParquePulsado(parque.id.toInt()) }
                    )
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = parque.nombre)
                    Text(text = parque.extension)
                    HorizontalDivider()
                }
            }
        }
    }
}
