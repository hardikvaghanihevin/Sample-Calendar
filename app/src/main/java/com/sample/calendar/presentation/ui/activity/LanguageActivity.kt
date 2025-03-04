package com.sample.calendar.presentation.ui.activity

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.preference.PreferenceManager
import com.sample.calendar.utility.object_.Constants.BASE_TAG
import com.sample.calendar.databinding.ActivityLanguageBinding
import com.sample.calendar.utility.class_.BackPressManager
import com.sample.calendar.utility.object_.Constants.FLAG_IS_FIRST_TIME_LAUNCH_APP
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class LanguageActivity : AppCompatActivity() {
    private val TAG = BASE_TAG + LanguageActivity::class.java

    private lateinit var binding: ActivityLanguageBinding

    private lateinit var backPressManager: BackPressManager
    private var isFirstTimeLaunchApp by Delegates.notNull<Boolean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLanguageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        isFirstTimeLaunchApp = PreferenceManager.getDefaultSharedPreferences(this).getBoolean(FLAG_IS_FIRST_TIME_LAUNCH_APP, true)

        // If it's the first launch, update the SharedPreferences
        if (isFirstTimeLaunchApp) {
            Handler(mainLooper).postDelayed({
                PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean(FLAG_IS_FIRST_TIME_LAUNCH_APP, false).apply()
                val options = ActivityOptions.makeCustomAnimation(this@LanguageActivity, android.R.anim.fade_in, android.R.anim.fade_out)
                startActivity(Intent(this, YearActivity::class.java), options.toBundle())
                finish()
            }, 1000)
        }

        backPressManager = BackPressManager.with()
        backPressManager.initialize(this)
        backPressManager.setCustomBackAction { finish() }


    }
}