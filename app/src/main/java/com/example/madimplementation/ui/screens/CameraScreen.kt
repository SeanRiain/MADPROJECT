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

@Composable
fun CameraScreen(navController: NavController) {

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

    // ---- Permission handling ----
    var permissionGranted by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(RequestPermission()) { granted ->
        permissionGranted = granted
    }

    LaunchedEffect(Unit) {
        launcher.launch(Manifest.permission.CAMERA)
    }

    // Don't show camera preview until permission is granted
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

        // Capture button
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
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

                            navController.navigate("photo_result")
                        }
                    }
                )
            }) {
                Text("Capture")
            }
        }
    } else {
        // Permission denied UI if needed
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Camera permission required")
        }
    }
}
