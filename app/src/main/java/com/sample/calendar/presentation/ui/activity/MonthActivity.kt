package com.sample.calendar.presentation.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sample.calendar.databinding.ActivityMonthBinding
import com.sample.calendar.utility.class_.BackPressManager
import com.sample.calendar.utility.object_.Constants.BASE_TAG
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MonthActivity : AppCompatActivity() {
    private val TAG = BASE_TAG + MonthActivity::class.java.simpleName

    private lateinit var binding: ActivityMonthBinding

    private lateinit var backPressManager: BackPressManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMonthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        backPressManager = BackPressManager.with()
        backPressManager.initialize(this)
        backPressManager.setCustomBackAction { finish() }


    }
}