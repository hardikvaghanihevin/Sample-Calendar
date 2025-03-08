package com.sample.calendar.utility.object_

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sample.calendar.MyApplication
import com.sample.calendar.R
import com.sample.calendar.databinding.ItemDrawerMenuBinding
import com.sample.calendar.databinding.LayoutDrawerBinding
import com.sample.calendar.presentation.ui.activity.CountryActivity
import com.sample.calendar.presentation.ui.activity.LanguageActivity
import com.sample.calendar.presentation.ui.activity.MonthActivity
import com.sample.calendar.presentation.ui.activity.SettingActivity
import com.sample.calendar.presentation.ui.activity.YearActivity
import com.sample.calendar.utility.object_.Constants.BASE_TAG
import com.sample.calendar.utility.object_.Constants.FLAG_ACTIVITY_COUNTRY
import com.sample.calendar.utility.object_.Constants.FLAG_ACTIVITY_LANGUAGE
import com.sample.calendar.utility.object_.Constants.FLAG_ACTIVITY_MONTH
import com.sample.calendar.utility.object_.Constants.FLAG_ACTIVITY_SETTING
import com.sample.calendar.utility.object_.Constants.FLAG_ACTIVITY_YEAR
import java.lang.ref.WeakReference
import javax.inject.Inject

object DrawerMenuManager {
    private val TAG = BASE_TAG + DrawerMenuManager::class.java.simpleName
    private var layoutDrawerBinding: LayoutDrawerBinding? = null
    private var isDrawerOpen = false
    private var drawerAdapter: DrawerMenuAdapter? = null
    private var selectedPosition: Int = -1

    // ✅ Use WeakReference to prevent memory leaks
    private var activityRef: WeakReference<Activity>? = null

    data class DrawerMenuItem(val id: Int, val title: String, val icon: Int, var isSelected: Boolean = false)// val icon: Int = -1, // Now just an Int to hold either attribute or drawable ID

    fun setupDrawer(activity: Activity, onItemSelected: (DrawerMenuItem) -> Unit) {
        activityRef = WeakReference(activity) // ✅ Store WeakReference to prevent leaks

        val rootView = activity.findViewById<ViewGroup>(android.R.id.content)

        val view = LayoutInflater.from(activity).inflate(R.layout.layout_drawer, rootView, false)
        layoutDrawerBinding = LayoutDrawerBinding.bind(view)
        rootView.addView(view) // Add drawer to the root layout

        layoutDrawerBinding?.layoutDrawer?.apply {
            layoutDrawerBinding?.drawerCloseContainer?.setOnClickListener {
                if (isDrawerOpen()) {
                    toggleDrawer()
                }
            }

            // Wait until layout is measured to set initial translation
            viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    viewTreeObserver.removeOnGlobalLayoutListener(this)
                    translationX = -width.toFloat() // Move drawer off-screen
                }
            })
        }

        val menuItems = listOf(
            DrawerMenuItem(FLAG_ACTIVITY_YEAR, "Year", R.drawable.icon_year),
            DrawerMenuItem(FLAG_ACTIVITY_MONTH, "Month", R.drawable.icon_month),
            DrawerMenuItem(FLAG_ACTIVITY_COUNTRY, "Country", R.drawable.icon_select_country),
            DrawerMenuItem(FLAG_ACTIVITY_LANGUAGE, "Language", R.drawable.icon_select_language),
            DrawerMenuItem(FLAG_ACTIVITY_SETTING, "Setting", R.drawable.icon_setting),
        )

        drawerAdapter = DrawerMenuAdapter(onItemSelected)
        layoutDrawerBinding?.drawerRecyclerView?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = drawerAdapter
        }
        drawerAdapter!!.submitList(menuItems)

        // Update selection for the current activity
        updateSelection(activity)
    }

    fun updateSelection(activity: Activity) {
        selectedPosition = when (activity) {
            is YearActivity -> 0
            is MonthActivity -> 1
            is CountryActivity -> 2
            is LanguageActivity -> 3
            is SettingActivity -> 4
            else -> -1
        }

        drawerAdapter?.setSelectedPosition(selectedPosition)
    }


    fun toggleDrawer() {
        layoutDrawerBinding?.layoutDrawer?.apply {
            if (isDrawerOpen) {
                // Close drawer
                closeDrawer()
            } else {
                // Open drawer
                openDrawer()
            }
            isDrawerOpen = !isDrawerOpen
        }
    }

    fun closeDrawer(onClosed: (() -> Unit)? = null) {
        layoutDrawerBinding?.layoutDrawer?.apply {
            animate()
                .translationX(-width.toFloat())
                .setInterpolator(AccelerateDecelerateInterpolator())
                .setDuration(200)
                .withEndAction {
                    isDrawerOpen = false
                    onClosed?.invoke() // Execute action after closing
                }
                .start()
        }
    }
    private fun openDrawer() {
        layoutDrawerBinding?.layoutDrawer?.apply {
            animate()
                .translationX(0f)
                .setInterpolator(AccelerateDecelerateInterpolator())
                .setDuration(200)
                .start()
        }
    }
    fun isDrawerOpen() = isDrawerOpen


    private class DrawerMenuAdapter(private val onClick: (DrawerMenuItem) -> Unit) :
        ListAdapter<DrawerMenuItem, DrawerMenuAdapter.DrawerMenuViewHolder>(DiffCallback) {

        private var selectedPosition: Int = 0//RecyclerView.NO_POSITION

        inner class DrawerMenuViewHolder(private val binding: ItemDrawerMenuBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun bind(item: DrawerMenuItem, position: Int) {
                binding.menuTitle.text = item.title
                binding.menuIcon.setImageDrawable(getDrawableFromAttribute(binding.root.context, item.icon))

                if (position == selectedPosition) {
                    binding.menuIcon.setColorFilter(ContextCompat.getColor(binding.root.context, R.color.accent_primary))
                    binding.menuTitle.setTextColor(ContextCompat.getColor(binding.root.context, R.color.accent_primary))
                    binding.root.setBackgroundColor(ContextCompat.getColor(binding.root.context, android.R.color.transparent))//R.color.background_primary
                } else {
                    binding.menuIcon.setColorFilter(ContextCompat.getColor(binding.root.context, R.color.text_primary))
                    binding.menuTitle.setTextColor(ContextCompat.getColor(binding.root.context, R.color.text_primary))
                    binding.root.setBackgroundColor(ContextCompat.getColor(binding.root.context, android.R.color.transparent))
                }

                binding.root.setOnClickListener {
                    val previousSelected = selectedPosition
                    selectedPosition = position

                    notifyItemChanged(previousSelected)  // Notify previous selection
                    notifyItemChanged(selectedPosition) // Notify new selection

                    onClick(item)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrawerMenuViewHolder {
            val binding = ItemDrawerMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return DrawerMenuViewHolder(binding)
        }

        override fun onBindViewHolder(holder: DrawerMenuViewHolder, position: Int) {
            holder.bind(getItem(position), position)
        }

        @SuppressLint("NotifyDataSetChanged")
        fun setSelectedPosition(position: Int) {
            selectedPosition = position
            notifyDataSetChanged() // Refresh adapter
        }

        companion object {
            private val DiffCallback = object : DiffUtil.ItemCallback<DrawerMenuItem>() {
                override fun areItemsTheSame(oldItem: DrawerMenuItem, newItem: DrawerMenuItem) = oldItem.id == newItem.id
                override fun areContentsTheSame(oldItem: DrawerMenuItem, newItem: DrawerMenuItem) = oldItem == newItem
            }
        }
    }

    // Function to resolve attribute-based drawable
    fun getDrawableFromAttribute(context: Context, attr: Int): Drawable? {
        if (attr <= 0) return null // Return null for invalid or default cases

        val typedValue = TypedValue()
        return if (context.theme.resolveAttribute(attr, typedValue, true) && typedValue.resourceId != 0) {
            // Attribute references a drawable resource
            ContextCompat.getDrawable(context, typedValue.resourceId)
        } else {
            // Fallback to directly using the provided attr as a drawable resource ID
            ContextCompat.getDrawable(context, attr).takeIf { attr != 0 }
        }
    }
}
