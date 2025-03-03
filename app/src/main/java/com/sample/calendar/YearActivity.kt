package com.sample.calendar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.sample.calendar.Constants.BASE_TAG
import com.sample.calendar.databinding.ActivityYearBinding

class YearActivity : AppCompatActivity() {
    private val TAG = BASE_TAG + YearActivity::class.java.simpleName

    private lateinit var binding:ActivityYearBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityYearBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup drawer menu inside the MotionLayout panel
        DrawerMenuManager.setupDrawer(this) { selectedItem ->
            Log.d(TAG, "Selected: ${selectedItem.title} ${selectedItem.id}")
            when(selectedItem.id){
                1 -> { startActivity(Intent(this, YearActivity::class.java)) ; finish() }
                2 -> { startActivity(Intent(this, MonthActivity::class.java)) }
            }
            DrawerMenuManager.toggleDrawer()
        }

        // Set click listener for a button to toggle the drawer
        binding.root.setOnClickListener {
            DrawerMenuManager.toggleDrawer()
        }
    }

}