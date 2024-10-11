package com.abdi.myquran

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var surahAdapter: SurahAdapter
    private var surahList: List<Surah> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerViewSurah)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Ambil data dari API
        // Contoh penggunaan untuk mendapatkan daftar surat Al-Qur'an
        RetrofitClient.quranApiService.getSurahList().enqueue(object : Callback<SurahResponse> {
            override fun onResponse(call: Call<SurahResponse>, response: Response<SurahResponse>) {
                if (response.isSuccessful) {
                    val surahList = response.body()?.data ?: listOf()
                    // Lakukan sesuatu dengan data yang diterima
                    surahAdapter = SurahAdapter(surahList)
                    recyclerView.adapter = surahAdapter
                } else {
                    Log.e("API Error", "Response Code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<SurahResponse>, t: Throwable) {
                Log.e("API Failure", t.message ?: "Unknown error")
            }
        })

    }
}

