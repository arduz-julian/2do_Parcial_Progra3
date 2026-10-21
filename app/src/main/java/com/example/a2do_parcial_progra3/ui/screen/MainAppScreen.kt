package com.example.a2do_parcial_progra3.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Article
import androidx.compose.material.icons.filled.CurrencyExchange
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Products : Screen("products", "Productos", Icons.Default.ShoppingCart)
    object ExchangeRates : Screen("exchange_rates", "Divisas", Icons.Default.CurrencyExchange)
    object Posts : Screen("posts", "Publicaciones", Icons.AutoMirrored.Filled.Article)
}

@Composable
fun MainAppScreen() {
    val navController = rememberNavController()
    val items = listOf(
        Screen.Products,
        Screen.ExchangeRates,
        Screen.Posts
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                items.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                        label = { Text(screen.title) },
                        selected = currentRoute == screen.route,
                        onClick = {
                            if (currentRoute != screen.route) {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Products.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screen.Products.route) {
                ProductsScreen()
            }
            composable(Screen.ExchangeRates.route) {
                ExchangeRatesScreen()
            }
            composable(Screen.Posts.route) {
                PostsScreen()
            }
        }
    }
}
