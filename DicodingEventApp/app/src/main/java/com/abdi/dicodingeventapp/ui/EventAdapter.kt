package com.abdi.dicodingeventapp.ui

import android.content.Intent
import androidx.recyclerview.widget.ListAdapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.abdi.dicodingeventapp.databinding.ItemEventBinding
import com.abdi.dicodingeventapp.databinding.ItemEventHorizontalBinding // Import layout horizontal
import com.abdi.dicodingeventapp.data.response.ListEventsItem
import com.abdi.dicodingeventapp.ui.detail.DetailEventActivity
import com.bumptech.glide.Glide

class EventsAdapter(private val isUpcoming: Boolean = false) : ListAdapter<ListEventsItem, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    // ViewHolder untuk finished event
    class EventViewHolder(private val binding: ItemEventBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: ListEventsItem) {
            binding.tvTitle.text = event.name

            Glide.with(binding.root.context)
                .load(event.mediaCover)
                .into(binding.imgEvent)

            // Aksi saat item diklik
            binding.root.setOnClickListener {
                val intent = Intent(binding.root.context, DetailEventActivity::class.java).apply {
                    putExtra("id", event.id)
                }
                binding.root.context.startActivity(intent)
            }
        }
    }

    // ViewHolder untuk upcoming event
    class HomeUpcomingEventViewHolder(private val binding: ItemEventHorizontalBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: ListEventsItem) {
            binding.tvTitle.text = event.name

            Glide.with(binding.root.context)
                .load(event.mediaCover)
                .into(binding.imgEvent)

            // Aksi saat item diklik
            binding.root.setOnClickListener {
                val intent = Intent(binding.root.context, DetailEventActivity::class.java).apply {
                    putExtra("id", event.id)
                }
                binding.root.context.startActivity(intent)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (isUpcoming) {
            VIEW_TYPE_UPCOMING
        } else {
            VIEW_TYPE_FINISHED
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_UPCOMING) {
            val binding = ItemEventHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            HomeUpcomingEventViewHolder(binding)
        } else {
            val binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            EventViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val event = getItem(position)
        if (holder is HomeUpcomingEventViewHolder) {
            holder.bind(event)
        } else if (holder is EventViewHolder) {
            holder.bind(event)
        }
    }

    companion object {
        private const val VIEW_TYPE_UPCOMING = 1
        private const val VIEW_TYPE_FINISHED = 2

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
