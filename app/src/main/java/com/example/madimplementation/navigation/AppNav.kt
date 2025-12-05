package com.example.madimplementation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.madimplementation.ui.screens.*
import com.example.madimplementation.viewmodel.MainViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object Intent : Screen("intent")
    object Category : Screen("category")
    object Camera : Screen("camera")
    object PhotoResult : Screen("photo_result")
    object InputItem : Screen("input_item")
    object Info : Screen("info")
}

@Composable
fun AppNav(navController: NavHostController) {

    val mainViewModel: MainViewModel = viewModel()


    NavHost(navController = navController, startDestination = Screen.Welcome.route) {
        composable(Screen.Welcome.route) { WelcomeScreen(navController, mainViewModel) }
        composable(Screen.Intent.route) { IntentScreen(navController, mainViewModel) }
        composable(Screen.Category.route) { CategoryScreen(navController, mainViewModel) }
        composable(Screen.Camera.route) { CameraScreen(navController, mainViewModel) }

        composable(Screen.InputItem.route) { ItemInputScreen(navController, mainViewModel) }
        //composable(Screen.PhotoResult.route) { PhotoResultScreen(navController) }

        composable(Screen.Info.route) { ItemInfoScreen(navController, mainViewModel) }
    }
}
