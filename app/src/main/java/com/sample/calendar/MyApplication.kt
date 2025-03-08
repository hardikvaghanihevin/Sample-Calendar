package com.sample.calendar

import android.app.Activity
import android.app.Application
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.sample.calendar.utility.object_.KeyBoardManager
import dagger.hilt.android.HiltAndroidApp
import java.lang.ref.WeakReference
import javax.inject.Inject

@HiltAndroidApp
class MyApplication: Application(), DefaultLifecycleObserver {

    override fun onCreate() {
        super<Application>.onCreate()
        instance = this
    }
    companion object {
        @Volatile
        private var instance: MyApplication? = null

        fun getInstance(): MyApplication = instance ?: throw IllegalStateException("Application instance is not initialized")

    }

}