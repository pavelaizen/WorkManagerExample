package com.servicenow.workmanagerexample

import android.content.Context
import android.graphics.Color
import android.os.SystemClock
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class NitzanWorker(context:Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        SystemClock.sleep(1000)
        val color = inputData.getInt("color", Color.BLACK)
        ColorManager.color.postValue(color)
        Log.d("fafafa", "color changed from worker")
        return Result.success()
    }
}