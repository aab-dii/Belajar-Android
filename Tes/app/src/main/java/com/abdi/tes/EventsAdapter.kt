package com.abdi.tes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.abdi.tes.databinding.ItemEventBinding
import com.bumptech.glide.Glide

class EventsAdapter(private val onItemClick: (EventDetail) -> Unit) : ListAdapter<ListEventsItem, EventsAdapter.EventViewHolder>(DIFF_CALLBACK) {

    class EventViewHolder(val binding: ItemEventBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(event: ListEventsItem){

            binding.tvTitle.text = event.name
            Glide.with(binding.root.context)
                .load(event.mediaCover)
                .into(binding.imgEvents)
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

    private fun ListEventsItem.toEventDetail(): EventDetail {
        // Pastikan untuk mengisi semua data yang diperlukan
        return EventDetail(
            id = this.id,
            name = this.name,
            summary = this.summary,
            description = this.description,
            imageLogo = this.imageLogo,
            mediaCover = this.mediaCover,
            category = this.category,
            ownerName = this.ownerName,
            cityName = this.cityName,
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
