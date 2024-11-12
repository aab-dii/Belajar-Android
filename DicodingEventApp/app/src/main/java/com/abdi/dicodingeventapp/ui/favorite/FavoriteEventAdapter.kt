package com.abdi.dicodingeventapp.ui.favorite

import android.content.Intent
import androidx.recyclerview.widget.ListAdapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.abdi.dicodingeventapp.data.local.entity.EventEntity
import com.abdi.dicodingeventapp.databinding.ItemEventBinding
import com.abdi.dicodingeventapp.ui.detail.DetailEventActivity
import com.bumptech.glide.Glide

class FavoriteEventsAdapter : ListAdapter<EventEntity, FavoriteEventsAdapter.EventViewHolder>(DIFF_CALLBACK) {

    class EventViewHolder(private val binding: ItemEventBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: EventEntity) {
            binding.tvTitle.text = event.name
            Glide.with(binding.root.context)
                .load(event.mediaCover)
                .into(binding.imgEvent)

            binding.root.setOnClickListener {
                val intent = Intent(binding.root.context, DetailEventActivity::class.java).apply {
                    putExtra("EVENT_ID", event.id)
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
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<EventEntity>() {
            override fun areItemsTheSame(oldItem: EventEntity, newItem: EventEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: EventEntity, newItem: EventEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}
