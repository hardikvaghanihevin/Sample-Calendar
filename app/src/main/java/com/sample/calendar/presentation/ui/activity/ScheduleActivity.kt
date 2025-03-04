package com.sample.calendar.presentation.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sample.calendar.utility.object_.Constants.BASE_TAG
import com.sample.calendar.databinding.ActivityScheduleBinding
import com.sample.calendar.utility.class_.BackPressManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScheduleActivity : AppCompatActivity() {
    private val TAG = BASE_TAG + SettingActivity::class.java

    private lateinit var binding: ActivityScheduleBinding

    private lateinit var backPressManager: BackPressManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        backPressManager = BackPressManager.with()
        backPressManager.initialize(this)
        backPressManager.setCustomBackAction { finish() }
    }
}