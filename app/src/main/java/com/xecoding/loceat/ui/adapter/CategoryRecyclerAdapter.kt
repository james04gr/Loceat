package com.xecoding.loceat.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.xecoding.loceat.R
import com.xecoding.loceat.model.entities.CategoryGroup

class CategoryRecyclerAdapter : RecyclerView.Adapter<CategoryRecyclerAdapter.CategoryViewHolder>() {

    var categoryGroups = listOf<CategoryGroup>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categoryGroups[position])
    }

    override fun getItemCount(): Int = categoryGroups.size

    class CategoryViewHolder private constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var categoryName: TextView = itemView.findViewById(R.id.categoryName)
        var childRecyclerView: RecyclerView = itemView.findViewById(R.id.childRecyclerView)

        fun bind(categoryGroup: CategoryGroup) {
            categoryName.text = categoryGroup.categoryName

            val childRecyclerAdapter = VenueRecyclerAdapter().apply {
                venues = categoryGroup.venues
            }
            childRecyclerView.adapter = childRecyclerAdapter
        }

        companion object {
            fun from(parent: ViewGroup): CategoryViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.category_item, parent, false)
                return CategoryViewHolder(view)
            }
        }
    }
}