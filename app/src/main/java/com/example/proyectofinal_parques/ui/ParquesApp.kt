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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyectofinal_parques.R
import com.example.proyectofinal_parques.ui.Pantallas.PantallaJson
import com.example.proyectofinal_parques.ui.Pantallas.PantallaRoom
import com.example.proyectofinal_parques.ui.Pantallas.PantallaInsertarJson
import com.example.proyectofinal_parques.ui.Pantallas.PantallaInsertarRoom

// Enum class que define las pantallas y sus rutas
enum class PantallasParque(val route: String) {
    PantallaJson("pantalla_json"),
    PantallaRoom("pantalla_room"),
    InsertarJson("insertar_json"),
    InsertarRoom("insertar_room")
}

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
    val currentScreen = navController.currentBackStackEntry?.destination?.route ?: ""

    BottomAppBar(
        modifier = modifier
    ) {
        IconButton(
            onClick = { navController.navigate(PantallasParque.PantallaJson.route) }
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Listado JSON")
        }

        IconButton(
            onClick = { navController.navigate(PantallasParque.PantallaRoom.route) }
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
                PantallasParque.PantallaJson.route -> {
                    FloatingActionButton(
                        onClick = { navController.navigate(PantallasParque.InsertarJson.route) }
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Insertar JSON")
                    }
                }
                PantallasParque.PantallaRoom.route -> {
                    FloatingActionButton(
                        onClick = { navController.navigate(PantallasParque.InsertarRoom.route) }
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Insertar Room")
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = PantallasParque.PantallaJson.route,  // Pantalla inicial
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(PantallasParque.PantallaJson.route) {
                // Usar viewModel() para obtener la instancia del ViewModel
                PantallaJson(viewModel = viewModel)  // Ahora el ViewModel se obtiene directamente
            }
            composable(PantallasParque.PantallaRoom.route) {
                PantallaRoom(viewModel = viewModel)
            }
            composable(PantallasParque.InsertarJson.route) {
                PantallaInsertarJson(viewModel = viewModel)
            }
            composable(PantallasParque.InsertarRoom.route) {
                PantallaInsertarRoom(viewModel = viewModel)
            }
        }
    }
}
