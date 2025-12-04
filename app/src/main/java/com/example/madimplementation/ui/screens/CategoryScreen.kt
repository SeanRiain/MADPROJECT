package com.example.madimplementation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.ui.unit.dp
import com.example.madimplementation.navigation.Screen
import androidx.compose.ui.Modifier

@Composable
fun CategoryScreen(navController: NavController) {
    Column(Modifier.fillMaxSize().padding(24.dp)) {
        Text("Select Device Category", style = androidx.compose.material3.MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(12.dp))

        Button(onClick = { navController.navigate(Screen.Camera.route) }) { Text("Cameras") }
        Spacer(Modifier.height(8.dp))
        Button(onClick = { navController.navigate(Screen.Camera.route) }) { Text("Phones") }
        Spacer(Modifier.height(8.dp))
        Button(onClick = { navController.navigate(Screen.Camera.route) }) { Text("Computers") }
        Spacer(Modifier.height(8.dp))
        Button(onClick = { navController.navigate(Screen.Camera.route) }) { Text("Speakers") }
        Spacer(Modifier.height(8.dp))
        Button(onClick = { navController.navigate(Screen.Camera.route) }) { Text("Microphones") }
        Spacer(Modifier.height(8.dp))
        Button(onClick = { navController.navigate(Screen.Camera.route) }) { Text("Televisions") }
    }
}
