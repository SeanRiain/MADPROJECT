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
fun IntentScreen(navController: NavController) {
    Column(Modifier.fillMaxSize().padding(24.dp)) {
        Text("I would like to get help with:", style = androidx.compose.material3.MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(12.dp))

        Button(onClick = { navController.navigate(Screen.Category.route) }) { Text("User Experience") }
        Spacer(Modifier.height(8.dp))
        Button(onClick = { navController.navigate(Screen.Category.route) }) { Text("Buying the Right Product") }
        Spacer(Modifier.height(8.dp))
        Button(onClick = { navController.navigate(Screen.Category.route) }) { Text("Troubleshooting") }
    }
}
