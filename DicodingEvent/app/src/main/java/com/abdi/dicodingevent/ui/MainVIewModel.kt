package com.abdi.dicodingevent.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abdi.dicodingevent.retrofit.ApiConfig
import com.abdi.dicodingevent.response.EventResponse
import com.abdi.dicodingevent.response.ListEventsItem
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
