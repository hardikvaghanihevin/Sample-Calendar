package com.sample.calendar

import android.app.Application
import androidx.lifecycle.DefaultLifecycleObserver
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication: Application(), DefaultLifecycleObserver {
    override fun onCreate() {
        super<Application>.onCreate()
    }
}