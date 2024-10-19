package com.abdi.tes

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.abdi.tes.databinding.ActivityDetailBinding
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mendapatkan ID event dari Intent
        val eventId = intent.getIntExtra("id", -1)
        Log.d("EventsAdapter", "ID default di detail activity: $eventId")

        // Mengambil detail event
        detailViewModel.setID(eventId)

        // Observasi perubahan pada eventDetail
        detailViewModel.eventDetail.observe(this) { eventDetail ->
            if (eventDetail != null && !eventDetail.error) {  // Memastikan tidak ada error
                binding.tvTitle.text = eventDetail.event.name
                Glide.with(this)
                    .load(eventDetail.event.mediaCover)
                    .into(binding.imgEvent)
            } else {
                Log.e("DetailActivity", "Error: ${eventDetail?.message}")
            }
        }
    }
}
