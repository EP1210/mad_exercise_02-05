package com.example.movieappmad24.workers

import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager

class WorkManagerDatabaseRepository(context: Context) {

    private val workManager = WorkManager.getInstance(context)

    fun seedDatabaseWorkRequest() {
        val workRequestBuilder = OneTimeWorkRequestBuilder<DatabaseWorker>()

        workManager.enqueue(workRequestBuilder.build())
    }
}