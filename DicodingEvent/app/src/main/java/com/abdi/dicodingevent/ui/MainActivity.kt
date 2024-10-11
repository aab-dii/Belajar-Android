package com.abdi.dicodingevent.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abdi.dicodingevent.R
import com.abdi.dicodingevent.response.ListEventsItem

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels() // Mendapatkan instance ViewModel
    private lateinit var eventAdapter: EventAdapter
    private lateinit var recyclerView: RecyclerView // Deklarasi RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inisialisasi RecyclerView
        recyclerView = findViewById(R.id.recyclerView) // Mengambil referensi RecyclerView dari XML
        eventAdapter = EventAdapter(emptyList()) // Inisialisasi adapter dengan list kosong
        recyclerView.layoutManager = LinearLayoutManager(this) // Atur layout manager
        recyclerView.adapter = eventAdapter // Set adapter ke RecyclerView

        // Mengamati data dari ViewModel
        viewModel.events.observe(this) { events ->
            // Memperbarui adapter dengan data acara
            eventAdapter.updateEvents(events)
        }
    }
}
