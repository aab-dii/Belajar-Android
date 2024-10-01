package com.abdi.ultramanworld

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.abdi.ultramanworld.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ambil data yang dikirimkan dari MainActivity
        val filmName = intent.getStringExtra(EXTRA_NAME)
        val filmRating = intent.getStringExtra(EXTRA_RATING)
        val filmDescription = intent.getStringExtra(EXTRA_DESCRIPTION)
        val filmPhoto = intent.getIntExtra(EXTRA_PHOTO, 0)

        // Set judul, deskripsi, dan gambar yang diterima ke dalam view
        binding.tvDetailName.text = filmName
        binding.tvDetailRating.text = filmRating
        binding.tvDetailDescription.text = filmDescription
        binding.imgDetailPhoto.setImageResource(filmPhoto)
    }

    companion object {
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_RATING = "extra_rating"
        const val EXTRA_DESCRIPTION = "extra_description"
        const val EXTRA_PHOTO = "extra_photo"
    }
}
