package com.example.madimplementation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.*
import androidx.navigation.NavController
import androidx.compose.ui.unit.dp
import com.example.madimplementation.navigation.Screen
import androidx.compose.ui.Modifier
import com.example.madimplementation.viewmodel.MainViewModel
//this is allowing second argument in the function declaration and enabling the appnav to work


@Composable
fun ItemInputScreen(navController: NavController, mainViewModel: MainViewModel) {
    var brand by remember { mutableStateOf("") }
    var model by remember { mutableStateOf("") }

    Column(Modifier.fillMaxSize().padding(24.dp)) {
        Text("Manual Input", style = androidx.compose.material3.MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(12.dp))

        OutlinedTextField(value = brand, onValueChange = { brand = it }, label = { Text("Brand") })
        Spacer(Modifier.height(8.dp))

        OutlinedTextField(value = model, onValueChange = { model = it }, label = { Text("Model") })
        Spacer(Modifier.height(12.dp))
        //needed to clear photo cache when not using the camera and to set brand/model
        Button(onClick = {
            mainViewModel.setCapturedUri(null)
            mainViewModel.setBrand(brand)
            mainViewModel.setModel(model)
            //navigate to info screen; ItemInfoScreen will auto-save
            navController.navigate(Screen.Info.route)
        }) { Text("View Info") }

    }
}