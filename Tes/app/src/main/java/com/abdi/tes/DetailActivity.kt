package com.abdi.tes

import android.net.Uri // Pastikan kamu mengimpor Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.abdi.tes.databinding.ActivityDetailBinding
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val eventName = intent.getStringExtra(EXTRA_NAME)
        val eventPhoto = intent.getStringExtra(EXTRA_PHOTO)

        Glide.with(this)
            .load(eventPhoto) // eventPhoto adalah URL gambar
            .into(binding.imgEvent)

        binding.tvTitle.text = eventName
        // Mengonversi string ke Uri sebelum memanggil setImageURI
        eventPhoto?.let {
            binding.imgEvent.setImageURI(Uri.parse(it))
        }
    }

    companion object {
        const val EXTRA_PHOTO = "extra_photo"
        const val EXTRA_NAME = "extra_name"
    }
}
