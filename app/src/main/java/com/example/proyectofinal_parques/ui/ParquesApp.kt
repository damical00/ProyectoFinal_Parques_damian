package com.example.proyectofinal_parques.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.proyectofinal_parques.R
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.proyectofinal_parques.ui.Pantallas.PantallaInicio

enum class PantallasParque(@StringRes val titulo:Int) {
    Inicio(titulo= R.string.inicio),
    Insertar(titulo= R.string.insertar_parque),
    Actualizar (titulo = R.string.actualizar)
}

@Composable
fun ParquesApps(
    viewModel: ParquesViewModel = viewModel (factory = ParquesViewModel.Factory),
    navController: NavHostController = rememberNavController()
){
    val pilaRetroceso by navController.currentBackStackEntryAsState()

    val pantallaActual = PantallasParque.valueOf(
        pilaRetroceso?.destination?.route ?: PantallasParque.Inicio.name
    )

    Scaffold(
        topBar = {
            AppTopBar(
                pantallaActual = pantallaActual,
                puedeNavegarAtras = navController.previousBackStackEntry != null,
                onNavegarAtras = { navController.navigateUp() }
            )
        },
        floatingActionButton = {
            if(pantallaActual.titulo==R.string.inicio){
                FloatingActionButton(
                    onClick = {navController.navigate(route = PantallasParque.Insertar.name)}
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = stringResource(R.string.insertar_parque)
                    )
                }
            }
        }
    ){ innerPadding -> //correcto
        val uiState = viewModel.parqueUIState

        NavHost(
            navController = navController,
            startDestination = PantallasParque.Inicio.name,
            modifier = Modifier.padding(innerPadding)

        ) {
            //Grafo de rutas
            composable(route = PantallasParque.Inicio.name)
            PantallaInicio(
                appUiState = uiState,
                onParqueObtenido = { viewModel.obtenerParque(it) },
                onCocheEliminado = { viewModel.eliminarParque(it.toString()) },
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    pantallaActual: PantallasParque,
    puedeNavegarAtras: Boolean,
    onNavegarAtras: () -> Unit,
    modifier: Modifier = Modifier
){
    TopAppBar(
        title = { Text(text = stringResource(id = pantallaActual.titulo)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        navigationIcon = {
            if(puedeNavegarAtras) {
                IconButton(onClick = onNavegarAtras) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.atras)
                    )
                }
            }
        },
        modifier = modifier
    )
}