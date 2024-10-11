package com.abdi.dicodingeventapp.ui.upcoming

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abdi.dicodingeventapp.refactor.ApiConfig
import com.abdi.dicodingeventapp.response.EventResponse
import com.abdi.dicodingeventapp.response.ListEventsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpcomingViewModel : ViewModel() {

    private val _events = MutableLiveData<List<ListEventsItem>>() // Menggunakan List<ListEventsItem>
    val events: LiveData<List<ListEventsItem>> = _events

    fun fetchEvents() {
        ApiConfig.getApiService().getEvents().enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                if (response.isSuccessful) {
                    val events = response.body()?.listEvents ?: emptyList() // Jika null, berikan emptyList
                    _events.value = events
                } else {
                    Log.e("UpcomingViewModel", "Response not successful: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                Log.e("UpcomingViewModel", "API request failed: ${t.message}")
            }
        })
    }
}