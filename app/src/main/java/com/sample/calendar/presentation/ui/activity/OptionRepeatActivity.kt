package com.sample.calendar.presentation.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sample.calendar.R
import com.sample.calendar.databinding.ActivityOptionRepeatBinding
import com.sample.calendar.utility.class_.BackPressManager
import com.sample.calendar.utility.object_.Constants.BASE_TAG
import com.sample.calendar.utility.object_.DrawerMenuManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OptionRepeatActivity : AppCompatActivity() {
    private val TAG = BASE_TAG + OptionRepeatActivity::class.java

    private lateinit var binding: ActivityOptionRepeatBinding

    private lateinit var backPressManager: BackPressManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOptionRepeatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        backPressManager = BackPressManager.with()
        backPressManager.initialize(this)
        backPressManager.setCustomBackAction { finish() }
    }
}