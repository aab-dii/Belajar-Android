package com.abdi.myquran

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var ayatAdapter: AyatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Dapatkan nomor surah dari Intent
        val nomorSurah = intent.getIntExtra("nomor", 0)
        Log.d("DetailActivity", "Nomor Surah: $nomorSurah")

        // Setup RecyclerView
        recyclerView = findViewById(R.id.recyclerViewAyat)
        recyclerView.layoutManager = LinearLayoutManager(this)

        ayatAdapter = AyatAdapter(emptyList())
        recyclerView.adapter = ayatAdapter

        // Panggil API untuk mendapatkan detail surah
        getDetailSurah(nomorSurah)
    }

    private fun getDetailSurah(nomor: Int) { // Pastikan ApiClient sudah diimplementasikan
        RetrofitClient.quranApiService.getDetailSurah(nomor).enqueue(object : Callback<DetailSurahResponse> {
            override fun onResponse(call: Call<DetailSurahResponse>, response: Response<DetailSurahResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { detailSurahResponse ->
                        val surahData = detailSurahResponse.data
                        // Set judul surah
                        val textViewDetail: TextView = findViewById(R.id.textViewDetail)
                        textViewDetail.text = surahData.nama

                        // Set RecyclerView dengan list ayat
                        ayatAdapter = AyatAdapter(surahData.ayat)
                        recyclerView.adapter = ayatAdapter
                    }
                } else {
                    Log.e("DetailActivity", "Gagal mengambil data surah: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailSurahResponse>, t: Throwable) {
                Log.e("DetailActivity", "Error: ${t.message}")
            }
        })
    }
}
