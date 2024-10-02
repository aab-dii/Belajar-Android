package com.abdi.ultramanworld

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(){
    private lateinit var rvFilm: RecyclerView
    private lateinit var listFilmAdapter: ListFilmAdapter
    private val list = ArrayList<Film>()
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvFilm = findViewById(R.id.rv_film)
        rvFilm.setHasFixedSize(true)

        // Atur layout manager untuk RecyclerView
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvFilm.layoutManager = layoutManager

        // Ambil daftar film dan tampilkan
        list.addAll(getListFilms())
        showRecyclerGrid()

        toolbar = findViewById(R.id.myToolBar)
        setSupportActionBar(toolbar)
    }

    private fun getListFilms(): ArrayList<Film> {
        val dataName = resources.getStringArray(R.array.data_name)
        val dataRating = resources.getStringArray(R.array.data_rating)
        val dataYear = resources.getStringArray(R.array.data_year)
        val dataDescription = resources.getStringArray(R.array.data_description)
        val dataEpisode = resources.getStringArray(R.array.data_episode)
        val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
        val listFilm = ArrayList<Film>()

        for (i in dataName.indices) {
            val film = Film(dataName[i], dataDescription[i], dataRating[i], dataYear[i],dataEpisode[i], dataPhoto.getResourceId(i, -1))
            listFilm.add(film)
        }
        dataPhoto.recycle()
        return listFilm
    }

    private fun showRecyclerGrid() {
        rvFilm.layoutManager = GridLayoutManager(this, 2)
        listFilmAdapter = ListFilmAdapter(this, list) // true untuk grid
        rvFilm.adapter = listFilmAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true // Mengembalikan true agar menu dapat ditampilkan
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean { // Ganti MenuItem? menjadi MenuItem
        return when (item.itemId) {
            R.id.about_page -> { // Gunakan item.itemId
                val moveProfile = Intent(this@MainActivity, ProfileActivity::class.java)
                startActivity(moveProfile)
                true // Mengembalikan true untuk menunjukkan bahwa event sudah ditangani
            }
            else -> super.onOptionsItemSelected(item) // Panggil super untuk item yang tidak ditangani
        }
    }


}
