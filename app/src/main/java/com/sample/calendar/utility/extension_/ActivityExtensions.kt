package com.sample.calendar.utility.extension_

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment

//📂 ActivityExtensions.kt (Best if you plan to add more Activity/Fragment extensions)
//📂 IntentExtensions.kt (If you only focus on intent-related extensions)
//📂 NavigationExtensions.kt (If you also plan to add navigation-related extensions)

fun Context.startActivityExt(intent: Intent, isFinish: Boolean = false) {
    startActivity(intent)
    if (this is Activity && isFinish){ finish() }
}

fun Fragment.startActivityExt(intent: Intent, isFinish: Boolean = false) {
    requireContext().startActivity(intent)
    if (this.activity is Activity && isFinish){ this.activity?.finish() }
}