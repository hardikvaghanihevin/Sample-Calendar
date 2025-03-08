package com.sample.calendar.presentation.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sample.calendar.R
import com.sample.calendar.databinding.ActivityOptionAlertBinding
import com.sample.calendar.utility.class_.BackPressManager
import com.sample.calendar.utility.object_.Constants.BASE_TAG
import com.sample.calendar.utility.object_.DrawerMenuManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OptionAlertActivity : AppCompatActivity() {
    private val TAG = BASE_TAG + OptionAlertActivity::class.java

    private lateinit var binding: ActivityOptionAlertBinding

    private lateinit var backPressManager: BackPressManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOptionAlertBinding.inflate(layoutInflater)
        setContentView(binding.root)

        backPressManager = BackPressManager.with()
        backPressManager.initialize(this)
        backPressManager.setCustomBackAction { finish() }
    }
}