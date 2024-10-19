package com.abdi.tes

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdi.tes.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: EventsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mengatur LayoutManager untuk RecyclerView
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager

        // Inisialisasi adapter
        adapter = EventsAdapter(this)
        binding.recyclerView.adapter = adapter

        // Memanggil API untuk mendapatkan daftar event
        getEventList()
    }

    private fun getEventList() {
        val client = ApiConfig.getApiService().getEvents()
        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(
                call: Call<EventResponse>,
                response: Response<EventResponse>
            ) {
                if (response.isSuccessful) {
                    val eventList = response.body()?.listEvents
                    if (eventList != null) {
                        // Submit list ke adapter
                        adapter.submitList(eventList)
                    } else {
                        Log.e("MainActivity", "Daftar event kosong")
                    }
                } else {
                    Log.e("MainActivity", "Response tidak berhasil: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                Log.e("MainActivity", "Gagal mengambil data: ${t.message}")
            }
        })
    }
}
