package com.abdi.ultramanworld

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    // Deklarasi RecyclerView untuk Series dan Movies
    private lateinit var rvFilmSeries: RecyclerView
    private lateinit var rvFilmMovies: RecyclerView

    // Deklarasi Adapter untuk Series dan Movies
    private lateinit var listFilmSeriesAdapter: ListFilmAdapter
    private lateinit var listFilmMoviesAdapter: ListFilmAdapter

    // Deklarasi list untuk Series dan Movies
    private val listFilm = ArrayList<Film>()
    private val listSeries = ArrayList<Film>()
    private val listMovies = ArrayList<Film>()
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inisialisasi RecyclerView untuk Series
        rvFilmSeries = findViewById(R.id.rv_film_series)
        rvFilmSeries.setHasFixedSize(true)
        val seriesLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvFilmSeries.layoutManager = seriesLayoutManager

        // Inisialisasi RecyclerView untuk Movies
        rvFilmMovies = findViewById(R.id.rv_film_movies)
        rvFilmMovies.setHasFixedSize(true)
        val moviesLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvFilmMovies.layoutManager = moviesLayoutManager

        // Ambil data dan pisahkan menjadi Series dan Movies
        separateSeriesAndMovies()

        // Tampilkan data pada RecyclerView
        showRecyclerList()

        // Inisialisasi Toolbar
        toolbar = findViewById(R.id.myToolBar)
        setSupportActionBar(toolbar)

        // Inisialisasi CardView
        val cardView: CardView = findViewById(R.id.card_view)
        cardView.setOnClickListener {
            // Pastikan listFilm memiliki setidaknya 7 item
            if (listFilm.size > 6) {
                val selectedFilm = listFilm[6] // Ambil item ke-7 (indeks ke-6)

                // Intent untuk berpindah ke DetailActivity
                val intent = Intent(this, DetailActivity::class.java).apply {
                    putExtra(DetailActivity.EXTRA_NAME, selectedFilm.name)
                    putExtra(DetailActivity.EXTRA_DESCRIPTION, selectedFilm.description)
                    putExtra(DetailActivity.EXTRA_RATING, selectedFilm.rating)
                    putExtra(DetailActivity.EXTRA_YEAR, selectedFilm.year)
                    putExtra(DetailActivity.EXTRA_EPISODE, selectedFilm.episode)
                    putExtra(DetailActivity.EXTRA_PHOTO, selectedFilm.photo)
                }

                // Pindah ke DetailActivity
                startActivity(intent)
            } else {
                // Jika listFilm tidak memiliki item ke-7, tampilkan pesan error
                Toast.makeText(this, "Film pada indeks ke-7 tidak ditemukan!", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    // Fungsi untuk mengambil data dan memisahkan menjadi Series dan Movies
    private fun separateSeriesAndMovies() {
        val dataName = resources.getStringArray(R.array.data_name)
        val dataRating = resources.getStringArray(R.array.data_rating)
        val dataYear = resources.getStringArray(R.array.data_year)
        val dataDescription = resources.getStringArray(R.array.data_description)
        val dataEpisode = resources.getStringArray(R.array.data_episode)
        val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
        val dataJenis = resources.getStringArray(R.array.data_jenis)

        for (i in dataName.indices) {
            val film = Film(
                dataName[i],
                dataDescription[i],
                dataRating[i],
                dataYear[i],
                dataEpisode[i],
                dataJenis[i], // Menambahkan jenis ke film
                dataPhoto.getResourceId(i, -1)
            )

            // Pisahkan data berdasarkan jenis (Series atau Movie)
            if (dataJenis[i] == "Series") {
                listSeries.add(film)
            } else if (dataJenis[i] == "Movie") {
                listMovies.add(film)
            }

            // Tambahkan semua film ke listFilm
            listFilm.add(film)
        }
        dataPhoto.recycle()
    }

    // Fungsi untuk menampilkan data pada RecyclerView Series dan Movies
    private fun showRecyclerList() {
        // Tampilkan data pada RecyclerView Series
        listFilmSeriesAdapter = ListFilmAdapter(this, listSeries)
        rvFilmSeries.adapter = listFilmSeriesAdapter

        // Tampilkan data pada RecyclerView Movies
        listFilmMoviesAdapter = ListFilmAdapter(this, listMovies)
        rvFilmMovies.adapter = listFilmMoviesAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true // Mengembalikan true agar menu dapat ditampilkan
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.about_page -> {
                val moveProfile = Intent(this@MainActivity, ProfileActivity::class.java)
                startActivity(moveProfile)
                true // Mengembalikan true untuk menunjukkan bahwa event sudah ditangani
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
