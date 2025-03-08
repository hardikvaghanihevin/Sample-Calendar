package com.sample.calendar.utility.object_

import android.app.Activity
import android.content.Context
import android.text.InputType
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.SearchView
import com.sample.calendar.utility.object_.Constants.BASE_TAG

object KeyBoardManager {
    private val TAG = BASE_TAG + "KeyboardManager"

    /**
     * Hide the keyboard using the current focus in an Activity.
     * @param activity The current activity.
     */
    fun hideKeyboard(activity: Activity, view: View? = activity.currentFocus) {
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        view?.let {
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    /**
     * Hide the keyboard from the screen.
     * @param context The application or activity context.
     * @param view Any view from the current window (used to get the token).
     */
    fun hideKeyboard(context: Context, view: View) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }


    /**
     * ########################################################################
     *
     * Show the keyboard for a given view.
     * @param context The application or activity context.
     * @param view The view that will receive focus and show the keyboard.
     */
    fun showKeyboard(context: Context, view: View) {
        view.requestFocus()
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }

    /**
     * Show the keyboard for a given EditText with a specific input type.
     * @param context The application or activity context.
     * @param editText The EditText that will receive focus and show the keyboard.
     * @param inputType The input type for the keyboard (e.g., text, number, password). Default is text.
     */
    fun showKeyboard(context: Context, editText: EditText, inputType: Int = InputType.TYPE_CLASS_TEXT) {
        editText.inputType = inputType
        editText.requestFocus()
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
    }

    /**
     * Show the keyboard for a SearchView.
     * @param context The application or activity context.
     * @param searchView The SearchView that will receive focus and show the keyboard.
     */
    fun showKeyboardForSearchView(context: Context, searchView: SearchView) {
        searchView.isIconified = false // Expand SearchView
        searchView.requestFocus()

        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(searchView.findFocus(), InputMethodManager.SHOW_IMPLICIT)
    }
}