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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment

@Composable
fun ItemInfoScreen(navController: NavController, mainViewModel: MainViewModel) {
    Column(Modifier.fillMaxSize().padding(24.dp)) {

        val photoUri by mainViewModel.capturedUri.collectAsState()
        val brand by mainViewModel.brand.collectAsState()
        val model by mainViewModel.model.collectAsState()
        val category by mainViewModel.category.collectAsState()
        val intentState by mainViewModel.intent.collectAsState()

        //Auto-save when this screen appears
        LaunchedEffect(Unit) {
            mainViewModel.saveCurrentItemIfNeeded()
        }

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

        Text("${brand ?: "[Item Brand]"}\n${model ?: "[Item Model]"}", style = androidx.compose.material3.MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(12.dp))

        //Intent-specific dummy text blocks
        when (intentState) {
            com.example.madimplementation.viewmodel.IntentType.SHOPPING -> {
                Text("If search category is shopping:\n\n[A description of what purpose the item has, its pros and cons, recommendations and requirements for installation and use, target audience etc. The buttons should let the user finish the process or scan a new item.]\n")
            }
            com.example.madimplementation.viewmodel.IntentType.UX -> {
                Text("If search category is UX:\n\n[Information and advice on getting the most out of the device's primary features, discussion of settings, reminders about secondary features, ideas for how to use it best depending on categories of need.]\n")
            }
            com.example.madimplementation.viewmodel.IntentType.TROUBLESHOOTING -> {
                Text("If search category is troubleshooting:\n\n[Information about common mistakes in setup, a list of possible problems with links on how to fix them.]\n")
            }
            else -> {
                Text("[General Information Placeholder]")
            }
        }

        Spacer(Modifier.height(12.dp))

        //Buttons: finish or scan new item
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Button(onClick = {
                mainViewModel.resetAll()
                navController.navigate(Screen.Welcome.route)
            }) { Text("Finish") }

            Button(onClick = {
                //keep intent/category, clear brand/model/photo and restart at scanner
                mainViewModel.setBrand(null)
                mainViewModel.setModel(null)
                mainViewModel.setCapturedUri(null)
                mainViewModel.resetAll()
                navController.navigate(Screen.Category.route)
            }) { Text("Scan New Item") }
        }
    }
}
