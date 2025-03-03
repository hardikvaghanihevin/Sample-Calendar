package com.sample.calendar

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
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
import com.sample.calendar.databinding.ItemDrawerMenuBinding
import com.sample.calendar.databinding.LayoutDrawerBinding

object DrawerMenuManager {
    private var layoutDrawerBinding: LayoutDrawerBinding? = null
    private var isDrawerOpen = false

    data class DrawerMenuItem(val id: Int, val title: String, val icon: Int, var isSelected: Boolean = false)// val icon: Int = -1, // Now just an Int to hold either attribute or drawable ID

    fun setupDrawer(context: Context, onItemSelected: (DrawerMenuItem) -> Unit) {
        val activity = context as Activity
        val rootView = activity.findViewById<ViewGroup>(android.R.id.content)

        val view = LayoutInflater.from(context).inflate(R.layout.layout_drawer, rootView, false)
        layoutDrawerBinding = LayoutDrawerBinding.bind(view)
        rootView.addView(view) // Add drawer to the root layout

        layoutDrawerBinding?.layoutDrawer?.apply {

            // Wait until layout is measured to set initial translation
            viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    viewTreeObserver.removeOnGlobalLayoutListener(this)
                    translationX = -width.toFloat() // Move drawer off-screen
                }
            })
        }

        val menuItems = listOf(
            DrawerMenuItem(1, "Year", R.drawable.icon_year),
            DrawerMenuItem(2, "Month", R.drawable.icon_month)
        )

        val adapter = DrawerMenuAdapter(onItemSelected)
        layoutDrawerBinding?.drawerRecyclerView?.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }
        adapter.submitList(menuItems)
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
    private fun closeDrawer() {
        layoutDrawerBinding?.layoutDrawer?.apply {
            animate()
                .translationX(-width.toFloat())
                .setInterpolator(AccelerateDecelerateInterpolator())
                .setDuration(300)
                .start()
        }
    }
    private fun openDrawer() {
        layoutDrawerBinding?.layoutDrawer?.apply {
            animate()
                .translationX(0f)
                .setInterpolator(AccelerateDecelerateInterpolator())
                .setDuration(300)
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
                    binding.root.setBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.background_primary))
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
