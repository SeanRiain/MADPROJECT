package com.example.madimplementation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.ui.unit.dp
import com.example.madimplementation.navigation.Screen

@Composable
fun ItemInfoScreen(navController: NavController) {
    Column(Modifier.fillMaxSize().padding(24.dp)) {
        Text("Item Brand\nItem Model", style = androidx.compose.material3.MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(12.dp))
        Text("If search category is shopping:\n\n[A description of what purpose the item has, its pros and cons, recommendations and requirements for installation and use, target audience etc. The buttons should let the user finish the process or scan a new item.]\n")
        Spacer(Modifier.height(12.dp))
        // More dummy text for UX and Troubleshooting
        Button(onClick = { navController.navigate(Screen.Home.route) }) { Text("Finish") }
    }
}
