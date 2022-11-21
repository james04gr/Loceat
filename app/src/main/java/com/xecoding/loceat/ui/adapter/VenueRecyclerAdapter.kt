package com.xecoding.loceat.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.xecoding.loceat.R
import com.xecoding.loceat.model.response.Venue

class VenueRecyclerAdapter : RecyclerView.Adapter<VenueRecyclerAdapter.VenueViewHolder>() {

    var venues = listOf<Venue>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VenueViewHolder {
        return VenueViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: VenueViewHolder, position: Int) {
        holder.bind(venues[position])
    }

    override fun getItemCount(): Int = venues.size

    class VenueViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var name: TextView = itemView.findViewById(R.id.venueName)
        var address: TextView = itemView.findViewById(R.id.venueAddress)

        fun bind(venue: Venue) {
            name.text = venue.name
            address.text = venue.location.formattedAddress.joinToString(",")
        }

        companion object {
            fun from(parent: ViewGroup): VenueViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.venue_item, parent, false)
                return VenueViewHolder(view)
            }
        }
    }
}