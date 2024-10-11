package com.abdi.dicodingeventapp.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.abdi.dicodingeventapp.databinding.ActivityDetailEventBinding
import com.bumptech.glide.Glide

class DetailEventActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailEventBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mengambil data dari Bundle
        val bundle = intent.extras
        if (bundle != null) {
            val eventName = bundle.getString(EXTRA_NAME)
            val eventPhoto = bundle.getString(EXTRA_PHOTO)
            val eventOwner = bundle.getString(EXTRA_OWNER)
            val eventBeginTime = bundle.getString(EXTRA_BEGIN_TIME)
            val eventQuota = bundle.getInt(EXTRA_QUOTA, 0)
            val eventRegistrants = bundle.getInt(EXTRA_REGISTRANTS, 0)
            val eventDescription = bundle.getString(EXTRA_DESCRIPTION)

            // Menghitung sisa kuota
            val remainingQuota = eventQuota - eventRegistrants

            // Binding data ke view
            Glide.with(this)
                .load(eventPhoto) // URL gambar (mediaCover)
                .into(binding.imgEvent) // Menampilkan gambar event

            binding.tvTitle.text = eventName // Menampilkan nama acara
            binding.tvOwner.text = eventOwner // Menampilkan penyelenggara acara
            binding.tvBeginTime.text = eventBeginTime // Menampilkan waktu acara
            binding.tvQuota.text = "Sisa kuota: $remainingQuota" // Menampilkan sisa kuota

            // Menampilkan deskripsi dengan HTML formatting
            binding.tvDescription.text = HtmlCompat.fromHtml(
                eventDescription ?: "", // Deskripsi event
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )
        }
    }

    companion object {
        const val EXTRA_PHOTO = "extra_photo"
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_OWNER = "extra_owner"
        const val EXTRA_BEGIN_TIME = "extra_begin_time"
        const val EXTRA_QUOTA = "extra_quota"
        const val EXTRA_REGISTRANTS = "extra_registrants"
        const val EXTRA_DESCRIPTION = "extra_description"
    }
}
