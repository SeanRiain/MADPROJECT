package com.example.madimplementation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.ui.unit.dp
import com.example.madimplementation.navigation.Screen
import androidx.compose.ui.Modifier
import com.example.madimplementation.viewmodel.MainViewModel
//this is allowing second argument in the function declaration and enabling the appnav to work

@Composable
fun CategoryScreen(navController: NavController, mainViewModel: MainViewModel) {
    Column(Modifier.fillMaxSize().padding(24.dp)) {
        Text("Select Device Category", style = androidx.compose.material3.MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(12.dp))

        //The line separation between the call to the viewmodel & the screen navigation needs to exist or else an error occurs,
        // you can also use a semi colon like i do below to accomplish the same fix
        Button(onClick = {mainViewModel.setCategory("Cameras")
                          navController.navigate(Screen.Camera.route) })
                          { Text("Cameras") }
        Spacer(Modifier.height(8.dp))

        //"When you click the button that says Phones, set the tracked category to "phones" and take the user to the screen called Camera"
        Button(onClick = {mainViewModel.setCategory("Phones"); navController.navigate(Screen.Camera.route) }) { Text("Phones") }
        Spacer(Modifier.height(8.dp))

        Button(onClick = {mainViewModel.setCategory("Computers"); navController.navigate(Screen.Camera.route) }) { Text("Computers") }
        Spacer(Modifier.height(8.dp))

        Button(onClick = {mainViewModel.setCategory("Speakers"); navController.navigate(Screen.Camera.route) }) { Text("Speakers") }
        Spacer(Modifier.height(8.dp))

        Button(onClick = {mainViewModel.setCategory("Microphones"); navController.navigate(Screen.Camera.route) }) { Text("Microphones") }
        Spacer(Modifier.height(8.dp))

        Button(onClick = {mainViewModel.setCategory("Televisions"); navController.navigate(Screen.Camera.route) }) { Text("Televisions") }
    }
}
