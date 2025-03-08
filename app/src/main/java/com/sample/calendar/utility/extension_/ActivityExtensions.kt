package com.sample.calendar.utility.extension_

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.fragment.app.Fragment
import com.sample.calendar.presentation.*
import com.sample.calendar.presentation.ui.activity.*
import com.sample.calendar.utility.object_.Constants.BASE_TAG
import com.sample.calendar.utility.object_.Constants.FLAG_ACTIVITY_COUNTRY
import com.sample.calendar.utility.object_.Constants.FLAG_ACTIVITY_LANGUAGE
import com.sample.calendar.utility.object_.Constants.FLAG_ACTIVITY_MONTH
import com.sample.calendar.utility.object_.Constants.FLAG_ACTIVITY_SETTING
import com.sample.calendar.utility.object_.Constants.FLAG_ACTIVITY_YEAR

//📂 ActivityExtensions.kt (Best if you plan to add more Activity/Fragment extensions)
//📂 IntentExtensions.kt (If you only focus on intent-related extensions)
//📂 NavigationExtensions.kt (If you also plan to add navigation-related extensions)
private val TAG = BASE_TAG + "Context"

/**
 * Extension function for starting an activity with flexible navigation options.
 *
 * @param intent The intent for the target activity.
 * @param isFinish If `true`, the current activity will be finished after starting the new activity.
 * @param shouldClearStack If `true`, clears the entire activity stack and starts a new instance of the target activity.
 * @param shouldSingleTop If `true`, brings an existing instance of the target activity to the top and removes all activities above it.
 *
 * **Use Cases:**
 *
 * 1️⃣ **Bring an Existing Activity to the Front Without Clearing Other Activities**
 *    ```kotlin
 *    startActivityExt(Intent(this, MainActivity::class.java))
 *    ```
 *    - Uses `FLAG_ACTIVITY_REORDER_TO_FRONT`
 *    - Moves `MainActivity` to the top if it already exists.
 *    - Other activities **remain** in the stack.
 *
 * 2️⃣ **Clear All Activities and Start Fresh**
 *    ```kotlin
 *    startActivityExt(Intent(this, MainActivity::class.java), shouldClearStack = true)
 *    ```
 *    - Uses `FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TASK`
 *    - Removes **all previous activities** and starts `MainActivity` fresh.
 *
 * 3️⃣ **Clear Activities Above but Keep the Current One**
 *    ```kotlin
 *    startActivityExt(Intent(this, MainActivity::class.java), shouldSingleTop = true)
 *    ```
 *    - Uses `FLAG_ACTIVITY_CLEAR_TOP | FLAG_ACTIVITY_SINGLE_TOP`
 *    - If `MainActivity` is already running, **it will not restart**.
 *    - Clears any activities **above** `MainActivity` in the stack.
 *
 * 4️⃣ **Finish the Current Activity After Navigation**
 *    ```kotlin
 *    startActivityExt(Intent(this, MainActivity::class.java), isFinish = true)
 *    ```
 *    - Starts `MainActivity` and **finishes the current activity**.
 *
 * **Important Note:**
 * - If you bring an existing activity to the front (`shouldSingleTop = true` or `FLAG_ACTIVITY_REORDER_TO_FRONT`),
 *   make sure to handle `onNewIntent()` in that activity to refresh UI.
 */
fun Activity.startActivityExt(intent: Intent, isFinish: Boolean = false, shouldClearStack: Boolean = false, shouldSingleTop: Boolean = false) {

    //require(!(shouldClearStack && shouldSingleTop)) { "Cannot use both shouldClearStack and shouldSingleTop at the same time." }//Todo: Handle manually IllegalArgumentException

    // Prevent conflicting flag usage
    if (shouldClearStack && shouldSingleTop) {
        throw IllegalArgumentException("Cannot use both shouldClearStack and shouldSingleTop at the same time.")
    }


    intent.flags = when {
        shouldClearStack -> Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        // Force behavior for YearActivity
        intent.component?.className == YearActivity::class.java.name -> Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        shouldSingleTop -> Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        else -> Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
    }

    startActivity(intent)

    if (isFinish) {
        finish()
    }
}


fun Fragment.startActivityExt(intent: Intent, isFinish: Boolean = false) {
    requireContext().startActivity(intent)
    if (this.activity is Activity && isFinish){ this.activity?.finish() }
}

fun Context.getActivityIntent(activityId: Int): Intent? {
    return when (activityId) {
        FLAG_ACTIVITY_YEAR -> Intent(this, YearActivity::class.java)
        FLAG_ACTIVITY_MONTH -> Intent(this, MonthActivity::class.java)
        FLAG_ACTIVITY_COUNTRY -> Intent(this, CountryActivity::class.java)
        FLAG_ACTIVITY_LANGUAGE -> Intent(this, LanguageActivity::class.java)
        FLAG_ACTIVITY_SETTING -> Intent(this, SettingActivity::class.java)
        else -> null
    }
}