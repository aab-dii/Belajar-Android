package com.abdi.dicodingeventapp.ui

import androidx.recyclerview.widget.ListAdapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Locale
import com.abdi.dicodingeventapp.databinding.ItemEventBinding
import com.abdi.dicodingeventapp.response.DetailEventResponse
import com.abdi.dicodingeventapp.response.ListEventsItem
import com.bumptech.glide.Glide

class EventsAdapter(private val onItemClick: (DetailEventResponse) -> Unit) : ListAdapter<ListEventsItem, EventsAdapter.EventViewHolder>(DIFF_CALLBACK) {

    class EventViewHolder(val binding: ItemEventBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(event: ListEventsItem){

            binding.tvTitle.text = event.name
            Glide.with(binding.root.context)
                .load(event.mediaCover)
                .into(binding.imgEvent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = getItem(position) // Menggunakan getItem dari ListAdapter
        holder.bind(event)

        holder.itemView.setOnClickListener {
            // Memanggil callback saat item diklik
            onItemClick(event.toEventDetail())
        }
    }

    private fun ListEventsItem.toEventDetail(): DetailEventResponse {
        // Pastikan untuk mengisi semua data yang diperlukan
        return DetailEventResponse(
            id = this.id,
            name = this.name,
            description = this.description,
            mediaCover = this.mediaCover,
            ownerName = this.ownerName,
            quota = this.quota,
            registrants = this.registrants,
            beginTime = this.beginTime,
            endTime = this.endTime,
            link = this.link
        )
    }

    companion object {
        // DiffUtil digunakan untuk mengelola pembaruan data dengan cara lebih efisien
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListEventsItem>() {
            override fun areItemsTheSame(oldItem: ListEventsItem, newItem: ListEventsItem): Boolean {
                // Bandingkan item berdasarkan ID atau properti unik lainnya
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ListEventsItem, newItem: ListEventsItem): Boolean {
                // Bandingkan seluruh konten item
                return oldItem == newItem
            }
        }
    }
}
