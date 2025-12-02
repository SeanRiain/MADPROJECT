package com.example.madimplementation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.madimplementation.ui.screens.*

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object Home : Screen("home")
    object Intent : Screen("intent")
    object Category : Screen("category")
    object Camera : Screen("camera")
    object PhotoResult : Screen("photo_result")
    object InputItem : Screen("input_item")
    object Info : Screen("info")
    object Troubleshoot : Screen("troubleshoot")
}

@Composable
fun AppNav(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Welcome.route) {
        composable(Screen.Welcome.route) { WelcomeScreen(navController) }
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.Intent.route) { IntentScreen(navController) }
        composable(Screen.Category.route) { CategoryScreen(navController) }
        composable(Screen.Camera.route) { CameraScreen(navController) }
        composable(Screen.PhotoResult.route) { PhotoResultScreen(navController) }
        composable(Screen.InputItem.route) { ItemInputScreen(navController) }
        composable(Screen.Info.route) { ItemInfoScreen(navController) }
        composable(Screen.Troubleshoot.route) { TroubleshootingScreen(navController) }
    }
}
