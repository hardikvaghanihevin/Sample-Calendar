package com.sample.calendar.utility.class_

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

class BackPressManager {

    // Example usage in a Activity/Fragment:
    /**
     * In your Fragment's onCreateView():
     *
     * ```kotlin
     * private lateinit var backPressManager: BackPressManager
     *
     * override fun onCreateView/onCreate(): View? {
     *  backPressManager = BackPressManager.with()
     *  backPressManager.initialize(this) // Initialize with the Fragment
     *
     * backPressManager.setCustomBackAction {
     *  // Your custom back action here
     *  Toast.makeText(requireContext(), "Fragment custom back action triggered", Toast.LENGTH_SHORT).show()
     *  fragmentManager?.popBackStack();
     *  finish();
     *  }
     * }
     * ```
     *
     * This initializes the BackPressManager in a Fragment, setting a custom action
     * that displays a Toast and pops the Fragment from the back stack when the
     * back button is pressed.
     */

    private var onBackPressedCallback: OnBackPressedCallback? = null
    private var customBackAction: (() -> Unit)? = null

    fun initialize(activity: FragmentActivity) {
        onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() { customBackAction?.invoke() ?: activity.finish() }
        }
        activity.onBackPressedDispatcher.addCallback(activity, onBackPressedCallback!!)
    }

    fun initialize(fragment: Fragment) {
        onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() { customBackAction?.invoke() ?: fragment.activity?.supportFragmentManager?.popBackStack() }
        }
        fragment.requireActivity().onBackPressedDispatcher.addCallback(fragment.viewLifecycleOwner, onBackPressedCallback!!)
    }

    fun setCustomBackAction(action: () -> Unit) {
        customBackAction = action
    }

    fun removeCustomBackAction() {
        customBackAction = null
    }

    fun enableBackPress(enabled: Boolean) {
        onBackPressedCallback?.isEnabled = enabled
    }

    companion object {
        fun with(): BackPressManager {
            return BackPressManager()
        }
    }
}
