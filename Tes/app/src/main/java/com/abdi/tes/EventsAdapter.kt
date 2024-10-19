package com.abdi.tes

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.abdi.tes.databinding.ItemEventBinding
import com.bumptech.glide.Glide

class EventsAdapter(private val context: Context) : ListAdapter<ListEventsItem, EventsAdapter.EventViewHolder>(DIFF_CALLBACK) {

    class EventViewHolder(private val binding: ItemEventBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: ListEventsItem) {
            binding.tvTitle.text = event.name

            Glide.with(binding.root.context)
                .load(event.mediaCover)
                .into(binding.imgEvents)

            // Aksi saat item diklik
            binding.root.setOnClickListener {
                Log.d("EventsAdapter", "Clicked event ID: ${event.id}")
                val intent = Intent(binding.root.context, DetailActivity::class.java).apply {
                    putExtra("id", event.id)
                }
                binding.root.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event)
    }

    companion object {
        // DiffUtil untuk meningkatkan efisiensi pembaruan data
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListEventsItem>() {
            override fun areItemsTheSame(oldItem: ListEventsItem, newItem: ListEventsItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ListEventsItem, newItem: ListEventsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}
