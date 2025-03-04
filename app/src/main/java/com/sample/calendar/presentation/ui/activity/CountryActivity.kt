package com.sample.calendar.presentation.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sample.calendar.utility.object_.Constants.BASE_TAG
import com.sample.calendar.databinding.ActivityCountryBinding
import com.sample.calendar.utility.class_.BackPressManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CountryActivity : AppCompatActivity() {
    private val TAG = BASE_TAG + CountryActivity::class.java

    private lateinit var binding: ActivityCountryBinding
    private lateinit var backPressManager: BackPressManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCountryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        backPressManager = BackPressManager.with()
        backPressManager.initialize(this)

        backPressManager.setCustomBackAction { finish() }
    }
}