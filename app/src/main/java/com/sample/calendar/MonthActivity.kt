package com.sample.calendar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sample.calendar.Constants.BASE_TAG
import com.sample.calendar.databinding.ActivityMonthBinding

class MonthActivity : AppCompatActivity() {
    private val TAG = BASE_TAG + MonthActivity::class.java.simpleName

    private lateinit var binding: ActivityMonthBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMonthBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}