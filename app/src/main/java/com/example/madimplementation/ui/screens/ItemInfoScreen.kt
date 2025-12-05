package com.example.madimplementation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.ui.unit.dp
import com.example.madimplementation.navigation.Screen
import androidx.compose.ui.Modifier
import com.example.madimplementation.viewmodel.MainViewModel
//this is allowing second argument in the function declaration and enabling the appnav to work
import coil.compose.AsyncImage
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue




@Composable
fun ItemInfoScreen(navController: NavController, mainViewModel: MainViewModel) {
    Column(Modifier.fillMaxSize().padding(24.dp)) {

        val photoUri by mainViewModel.capturedUri.collectAsState()

        if (photoUri != null) {
            coil.compose.AsyncImage(
                model = photoUri,
                contentDescription = "Captured item",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Spacer(Modifier.height(16.dp))
        }



        Text("Item Brand\nItem Model", style = androidx.compose.material3.MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(12.dp))

        Text("If search category is shopping:\n\n[A description of what purpose the item has, its pros and cons, recommendations and requirements for installation and use, target audience etc. The buttons should let the user finish the process or scan a new item.]\n")
        Spacer(Modifier.height(12.dp))
        //Unfinished
        Button(onClick = { navController.navigate(Screen.Welcome.route) }) { Text("Finish") }
    }
}
