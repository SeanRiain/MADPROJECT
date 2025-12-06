package com.example.madimplementation.ui.screens

import android.Manifest
import android.content.ContentValues
import android.os.Build
import android.provider.MediaStore
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import androidx.camera.core.ImageCaptureException
import com.example.madimplementation.viewmodel.MainViewModel
//this is allowing second argument in the function declaration and enabling the appnav to work
import com.example.madimplementation.navigation.Screen
import androidx.compose.ui.unit.dp


@Composable
fun CameraScreen(navController: NavController, mainViewModel: MainViewModel) {

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val executor: ExecutorService = remember { Executors.newSingleThreadExecutor() }

    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    val previewView = remember { PreviewView(context) }

    val imageCapture = remember {
        ImageCapture.Builder()
            .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
            .build()
    }

    //Permission handling
    var permissionGranted by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(RequestPermission()) { granted ->
        permissionGranted = granted
    }

    LaunchedEffect(Unit) {
        launcher.launch(Manifest.permission.CAMERA)
    }

    //Dispose executor to avoid leaks
    DisposableEffect(Unit) {
        onDispose {
            executor.shutdown()
        }
    }

    //Don't show camera preview until permission is granted
    if (permissionGranted) {
        AndroidView(
            factory = { previewView },
            modifier = Modifier.fillMaxSize()
        ) { view ->
            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()

                val preview = Preview.Builder().build().apply {
                    setSurfaceProvider(view.surfaceProvider)
                }

                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        CameraSelector.DEFAULT_BACK_CAMERA,
                        preview,
                        imageCapture
                    )
                } catch (e: Exception) {
                    Log.e("CameraScreen", "Camera binding failed", e)
                }
            }, ContextCompat.getMainExecutor(context))
        }

        //Buttons
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {

            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                //Capture Button
                Button(onClick = {
                    val name = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH)
                        .format(System.currentTimeMillis())

                    val contentValues = ContentValues().apply {
                        put(MediaStore.MediaColumns.DISPLAY_NAME, "IMG_$name.jpg")
                        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/MADimplementation")
                        }
                    }

                    val outputOptions = ImageCapture.OutputFileOptions
                        .Builder(
                            context.contentResolver,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            contentValues
                        )
                        .build()

                    imageCapture.takePicture(
                        outputOptions,
                        executor,
                        object : ImageCapture.OnImageSavedCallback {

                            override fun onError(exc: ImageCaptureException) {
                                Log.e("CameraScreen", "Capture failed: ${exc.message}")
                            }

                            override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                                val savedUri: Uri? = output.savedUri
                                Log.d("CameraScreen", "Saved: $savedUri")

                                //store Uri in ViewModel
                                mainViewModel.setCapturedUri(savedUri)

                                navController.navigate(Screen.Info.route)
                            }
                        }
                    )
                }) {
                    Text("Capture")
                }
                Spacer(modifier = Modifier.height(12.dp))

                //Input Manually Button
                Button(onClick = {
                    mainViewModel.setCapturedUri(null)   //clear previous photo
                    navController.navigate(Screen.InputItem.route)
                }) {
                    Text("Input Item Manually")
                }
            }
        }
    }
        else {
        //Permission denied UI
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Camera permission required")
        }
    }
}