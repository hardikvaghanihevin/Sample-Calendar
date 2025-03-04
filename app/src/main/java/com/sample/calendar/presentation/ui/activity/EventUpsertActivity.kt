package com.sample.calendar.presentation.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sample.calendar.utility.object_.Constants.BASE_TAG
import com.sample.calendar.databinding.ActivityEventUpsertBinding
import com.sample.calendar.utility.class_.BackPressManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EventUpsertActivity : AppCompatActivity() {
    private val TAG = BASE_TAG + EventUpsertActivity::class.java

    private lateinit var binding: ActivityEventUpsertBinding

    private lateinit var backPressManager: BackPressManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEventUpsertBinding.inflate(layoutInflater)
        setContentView(binding.root)

        backPressManager = BackPressManager.with()
        backPressManager.initialize(this)
        backPressManager.setCustomBackAction { finish() }
    }
}