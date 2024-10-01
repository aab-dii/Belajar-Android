package com.abdi.ultramanworld

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var rvFilm: RecyclerView
    private val list = ArrayList<Film>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvFilm = findViewById(R.id.rv_film)
        rvFilm.setHasFixedSize(true)

        list.addAll(getListFilms())
        showRecyclerList()

        val btnProfil: Button = findViewById(R.id.btn_profile)
        btnProfil.setOnClickListener(this)
    }



    private fun getListFilms(): ArrayList<Film> {
        val dataName = resources.getStringArray(R.array.data_name)
        val dataRating = resources.getStringArray(R.array.data_rating)
        val dataDescription = resources.getStringArray(R.array.data_description)
        val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
        val listFilm = ArrayList<Film>()

        for (i in dataName.indices) {
            val film = Film(dataName[i], dataDescription[i], dataRating[i], dataPhoto.getResourceId(i, -1))
            listFilm.add(film)
        }
        dataPhoto.recycle()
        return listFilm
    }

    private fun showRecyclerList() {
        rvFilm.layoutManager = LinearLayoutManager(this)
        val listFilmAdapter = ListFilmAdapter(this, list, false) // false untuk list
        rvFilm.adapter = listFilmAdapter
    }

    private fun showRecyclerGrid() {
        rvFilm.layoutManager = GridLayoutManager(this, 2)
        val listFilmAdapter = ListFilmAdapter(this, list, true) // true untuk grid
        rvFilm.adapter = listFilmAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true // Mengembalikan true agar menu dapat ditampilkan
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_list -> {
                showRecyclerList() // Panggil metode showRecyclerList
                true
            }
            R.id.action_grid -> {
                showRecyclerGrid() // Panggil metode showRecyclerGrid
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btn_profile -> {
                val moveProfile = Intent(this@MainActivity,ProfileActivity::class.java)
                startActivity(moveProfile)
            }
        }
    }
}
