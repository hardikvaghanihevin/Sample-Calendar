package com.sample.calendar.presentation.ui.activity

import android.annotation.SuppressLint
import android.app.ActivityOptions
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sample.calendar.databinding.ActivitySplashBinding

import android.content.Intent
import android.os.Build
import androidx.core.app.ActivityOptionsCompat
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.preference.PreferenceManager
import com.sample.calendar.R
import com.sample.calendar.utility.class_.BackPressManager
import com.sample.calendar.utility.object_.Constants
import com.sample.calendar.utility.object_.Constants.FLAG_IS_FIRST_TIME_LAUNCH_APP
import com.sample.calendar.utility.object_.DateUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private val TAG = Constants.BASE_TAG + SplashActivity::class.java.simpleName

    private lateinit var binding: ActivitySplashBinding

    private lateinit var backPressManager: BackPressManager
    val date = DateUtil.getCurrentDate()
    val day = DateUtil.getCurrentDay(isShort = false)

    companion object {
        var splashScrn: SplashScreen? = null
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT > 31) {
            splashScrn = installSplashScreen()
            splashScrn?.setKeepOnScreenCondition { false }
        }
        super.onCreate(savedInstanceState)

        // Inflate the binding and set the content view
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        backPressManager = BackPressManager.with()
        backPressManager.initialize(this)
        backPressManager.setCustomBackAction { finish() }

        binding.apply {
            tvDay.apply { text = day }
            tvDate.apply { text = date.toString() }
        }

        // Launch the next screen asynchronously
        CoroutineScope(Dispatchers.Main).launch {
            delay(100) // Short delay to mimic splash duration
            navigateToNextScreen()
        }
    }

    private suspend fun navigateToNextScreen() {
        withContext(Dispatchers.IO) {
            // Check if it's the first launch using SharedPreferences
            val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this@SplashActivity)
            val isFirstTimeLaunchApp = sharedPrefs.getBoolean(FLAG_IS_FIRST_TIME_LAUNCH_APP, true)

            val nextActivity = if (isFirstTimeLaunchApp) {
                LanguageActivity::class.java
            } else {
                YearActivity::class.java
            }

            withContext(Dispatchers.Main) {
                val intent = Intent(this@SplashActivity, nextActivity)

                val options = ActivityOptions.makeCustomAnimation(this@SplashActivity, android.R.anim.fade_in, android.R.anim.fade_out)

                startActivity(intent, options.toBundle())
                finish()
            }
        }
    }
}
//Handler(mainLooper).postDelayed({
//            startActivity(Intent(this, YearActivity::class.java))
//        }, 1000)