// ParquesApps.kt
package com.example.proyectofinal_parques.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyectofinal_parques.R
import com.example.proyectofinal_parques.ui.Pantallas.PantallaRoom
import com.example.proyectofinal_parques.ui.Pantallas.PantallaInsertarJson
import com.example.proyectofinal_parques.ui.Pantallas.PantallaInsertarRoom
import com.example.proyectofinal_parques.ui.Pantallas.PantallaJson
import androidx.lifecycle.viewmodel.compose.viewModel // <-- Asegúrate de tener esta importación

// Barra superior de la aplicación
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val canNavigateBack = navController.previousBackStackEntry != null

    TopAppBar(
        title = {
            Text(text = stringResource(id = R.string.app_name)) // O personaliza según lo que quieras mostrar
        },
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Atrás"
                    )
                }
            }
        },
        actions = {
            // Agregar cualquier otra acción que necesites en la barra superior
        },
        modifier = modifier
    )
}

// Barra inferior de navegación
@Composable
fun AppBottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val currentScreen = navController.currentBackStackEntry?.destination?.route

    BottomAppBar(
        modifier = modifier
    ) {
        IconButton(
            onClick = { navController.navigate("pantalla_json") }
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Listado JSON")
        }

        IconButton(
            onClick = { navController.navigate("pantalla_room") }
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Listado Room")
        }
    }
}

// Componente principal de la aplicación
@Composable
fun ParquesApps(
    viewModel: ParquesViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    navController: NavHostController = rememberNavController()
) {
    Scaffold(
        topBar = { AppTopBar(navController = navController) },  // Aquí pasamos el navController
        bottomBar = { AppBottomBar(navController = navController) },
        floatingActionButton = {
            // Detectamos qué pantalla está activa para mostrar el FloatingButton adecuado
            val currentScreen = navController.currentBackStackEntry?.destination?.route
            when (currentScreen) {
                "pantalla_json" -> {
                    FloatingActionButton(
                        onClick = { navController.navigate("insertar_json") }
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Insertar JSON")
                    }
                }
                "pantalla_room" -> {
                    FloatingActionButton(
                        onClick = { navController.navigate("insertar_room") }
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Insertar Room")
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "pantalla_json",  // Pantalla inicial
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("pantalla_json") {
                PantallaJson(viewModel = viewModel)  // Pasamos el ViewModel como argumento
            }
            composable("pantalla_room") {
                PantallaRoom(viewModel = viewModel)  // Lo pasamos a la pantalla
            }
            composable("insertar_json") {
                PantallaInsertarJson(viewModel = viewModel)  // Lo pasamos a la pantalla
            }
            composable("insertar_room") {
                PantallaInsertarRoom(viewModel = viewModel)  // Lo pasamos a la pantalla
            }
        }
    }
}
