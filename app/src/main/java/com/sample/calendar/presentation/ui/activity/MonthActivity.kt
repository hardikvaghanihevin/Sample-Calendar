package com.sample.calendar.presentation.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.sample.calendar.databinding.ActivityMonthBinding
import com.sample.calendar.utility.class_.BackPressManager
import com.sample.calendar.utility.extension_.getActivityIntent
import com.sample.calendar.utility.extension_.startActivityExt
import com.sample.calendar.utility.object_.CalendarDateGenerator
import com.sample.calendar.utility.object_.Constants
import com.sample.calendar.utility.object_.Constants.BASE_TAG
import com.sample.calendar.utility.object_.DrawerMenuManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MonthActivity : AppCompatActivity() {
    private val TAG = BASE_TAG + MonthActivity::class.java.simpleName

    private lateinit var binding: ActivityMonthBinding

    private lateinit var backPressManager: BackPressManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMonthBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()

        backPressManager = BackPressManager.with()
        backPressManager.initialize(this)
        backPressManager.setCustomBackAction { finish() }

        DrawerMenuManager.updateSelection(this)
        DrawerMenuManager.setupDrawer(this) { selectedItem ->
            Log.d(TAG, "Selected item: ${selectedItem.title} ${selectedItem.id}")

            val activityIntent = this.getActivityIntent(selectedItem.id)

            if (activityIntent != null) {
                DrawerMenuManager.closeDrawer { this.startActivityExt(activityIntent) }
            } else {
                DrawerMenuManager.toggleDrawer()
            }
        }

        // Set click listener for a button to toggle the drawer
        binding.sivNavigationIcon.setOnClickListener {
            DrawerMenuManager.toggleDrawer()
        }

    }
}