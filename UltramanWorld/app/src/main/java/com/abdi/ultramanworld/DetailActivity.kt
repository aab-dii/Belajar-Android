package com.abdi.ultramanworld

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.abdi.ultramanworld.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val filmName = intent.getStringExtra(EXTRA_NAME)
        val filmRating = intent.getStringExtra(EXTRA_RATING)
        val filmYear = intent.getStringExtra(EXTRA_YEAR)
        val filmDescription = intent.getStringExtra(EXTRA_DESCRIPTION)
        val filmEpisode = intent.getStringExtra(EXTRA_EPISODE)
        val filmPhoto = intent.getIntExtra(EXTRA_PHOTO, 0)

        binding.tvDetailName.text = filmName
        binding.tvDetailRating.text = filmRating
        binding.tvDetailYear.text = filmYear
        binding.tvDetailDescription.text = filmDescription
        binding.tvDetailEpisode.text = filmEpisode
        binding.imgDetailPhoto.setImageResource(filmPhoto)

        toolbar = findViewById(R.id.myToolBar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // Tombol kembali

        // Mengatur listener untuk tombol kembali
        toolbar.setNavigationOnClickListener {
            onBackPressed() // Kembali ke activity sebelumnya
        }
        // Inisialisasi dan set up UI...

        val shareButton: Button = findViewById(R.id.action_share)
        shareButton.setOnClickListener {
            shareText(filmName ?: "Film menarik!")
        }
    }

    private fun shareText(text: String) {
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, text)
            type = "text/plain"
        }
        startActivity(Intent.createChooser(shareIntent, "Bagikan melalui"))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true // Mengembalikan true agar menu dapat ditampilkan
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean { // Ganti MenuItem? menjadi MenuItem
        return when (item.itemId) {
            R.id.about_page -> { // Gunakan item.itemId
                val moveProfile = Intent(this@DetailActivity, ProfileActivity::class.java)
                startActivity(moveProfile)
                true // Mengembalikan true untuk menunjukkan bahwa event sudah ditangani
            }
            else -> super.onOptionsItemSelected(item) // Panggil super untuk item yang tidak ditangani
        }
    }

    companion object {
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_RATING = "extra_rating"
        const val EXTRA_YEAR = "extra_year"
        const val EXTRA_DESCRIPTION = "extra_description"
        const val EXTRA_EPISODE = "extra_episode"
        const val EXTRA_PHOTO = "extra_photo"
    }
}
