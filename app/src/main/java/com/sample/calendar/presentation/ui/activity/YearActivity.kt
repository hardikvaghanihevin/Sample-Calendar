package com.sample.calendar.presentation.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.sample.calendar.utility.object_.Constants.BASE_TAG
import com.sample.calendar.databinding.ActivityYearBinding
import com.sample.calendar.utility.class_.BackPressManager
import com.sample.calendar.utility.extension_.getActivityIntent
import com.sample.calendar.utility.extension_.startActivityExt
import com.sample.calendar.utility.object_.CalendarDateGenerator
import com.sample.calendar.utility.object_.Constants.FLAG_ACTIVITY_COUNTRY
import com.sample.calendar.utility.object_.Constants.FLAG_ACTIVITY_LANGUAGE
import com.sample.calendar.utility.object_.Constants.FLAG_ACTIVITY_MONTH
import com.sample.calendar.utility.object_.Constants.FLAG_ACTIVITY_SETTING
import com.sample.calendar.utility.object_.Constants.FLAG_ACTIVITY_YEAR
import com.sample.calendar.utility.object_.DrawerMenuManager
import com.sample.calendar.utility.object_.KeyBoardManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class YearActivity : AppCompatActivity() {
    private val TAG = BASE_TAG + YearActivity::class.java.simpleName

    private lateinit var binding: ActivityYearBinding

    private lateinit var backPressManager: BackPressManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityYearBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()

        backPressManager = BackPressManager.with()
        backPressManager.initialize(this)
        backPressManager.setCustomBackAction {
            if(DrawerMenuManager.isDrawerOpen()){
                DrawerMenuManager.toggleDrawer()
                return@setCustomBackAction
            }else{
                finish()
                DrawerMenuManager.updateSelection(this)
            }
        }

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

        binding.includedSearch.searchIcon.setOnClickListener {

            lifecycleScope.launch {
                val a = CalendarDateGenerator.generateCalendar(2024,2025, false)

                Log.e(TAG, "onCreate: ${a.first}", )
                Log.i(TAG, "onCreate: ${a.second}", )
                KeyBoardManager.showKeyboard(this@YearActivity,binding.edt)

            }
        }
    }
}