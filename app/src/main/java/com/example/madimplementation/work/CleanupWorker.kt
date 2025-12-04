package com.example.madimplementation.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import androidx.work.ExistingPeriodicWorkPolicy

import androidx.work.PeriodicWorkRequestBuilder
import java.util.concurrent.TimeUnit
import com.example.madimplementation.data.AppDatabase

class CleanupWorker(appContext: Context, params: WorkerParameters)
    : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        val dao = AppDatabase.getDatabase(applicationContext).scannedItemDao()

        // TODO: Add real cleanup logic (e.g., deleting old entries)
        return Result.success()
    }

    companion object {
        fun schedulePeriodicWork(context: Context) {
            val workRequest = PeriodicWorkRequestBuilder<CleanupWorker>(24, TimeUnit.HOURS)
                .build()

            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                "cleanup_worker",
                ExistingPeriodicWorkPolicy.KEEP,
                workRequest
            )
        }
    }
}
