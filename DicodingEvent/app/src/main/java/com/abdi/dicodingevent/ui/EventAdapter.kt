package com.abdi.dicodingevent.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abdi.dicodingevent.R
import com.abdi.dicodingevent.response.ListEventsItem
import com.abdi.dicodingevent.databinding.ItemEventBinding // Import binding class
import com.bumptech.glide.Glide

class EventAdapter(private var events: List<ListEventsItem>) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    inner class EventViewHolder(private val binding: ItemEventBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: ListEventsItem) {
            binding.tvTitle.text = event.name

            // Memuat gambar dengan Glide
            Glide.with(binding.root.context)
                .load(event.mediaCover) // Pastikan 'mediaCover' adalah URL gambar
                .into(binding.eventImg)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(events[position])
    }

    override fun getItemCount(): Int = events.size

    fun updateEvents(newEvents: List<ListEventsItem>) {
        events = newEvents
        notifyDataSetChanged()
    }
}
