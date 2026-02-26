package com.example.appkotlin.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.appkotlin.di.ViewModelFactory
import com.example.appkotlin.ui.detail.UserDetailScreen
import com.example.appkotlin.ui.login.LoginScreen
import com.example.appkotlin.ui.userlist.UserListScreen

@Composable
fun NavGraph(viewModelFactory: ViewModelFactory) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                viewModelFactory = viewModelFactory,
                onLoginSuccess = {
                    navController.navigate("user_list") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }
        composable("user_list") {
            UserListScreen(
                viewModelFactory = viewModelFactory,
                onUserClick = { userId ->
                    navController.navigate("user_detail/$userId")
                },
                onLogoutClick = {
                    navController.navigate("login") {
                        popUpTo("user_list") { inclusive = true }
                    }
                }
            )
        }
        composable(
            route = "user_detail/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.IntType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId") ?: 0
            UserDetailScreen(
                viewModelFactory = viewModelFactory,
                userId = userId,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
