package com.abdi.dicodingeventapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abdi.dicodingeventapp.refactor.ApiConfig
import com.abdi.dicodingeventapp.response.EventResponse
import com.abdi.dicodingeventapp.response.ListEventsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _events = MutableLiveData<List<ListEventsItem>>() // Menggunakan List<ListEventsItem>
    val events: LiveData<List<ListEventsItem>> = _events

    init {
        fetchEvents()
    }

    private fun fetchEvents() {
        ApiConfig.getApiService().getEvents().enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                if (response.isSuccessful) {
                    // Ambil listEvents dari response dan set ke LiveData
                    _events.value = response.body()?.listEvents
                } else {
                    // Tangani jika response tidak sukses
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                // Tangani error
            }
        })
    }
}