package com.abdi.dicodingeventapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abdi.dicodingeventapp.data.refactor.ApiConfig
import com.abdi.dicodingeventapp.data.response.EventResponse
import com.abdi.dicodingeventapp.data.response.ListEventsItem
import com.abdi.dicodingeventapp.utils.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val _upcomingEvents = MutableLiveData<List<ListEventsItem>>()
    val upcomingEvents: LiveData<List<ListEventsItem>> = _upcomingEvents
    private val _finishedEvents = MutableLiveData<List<ListEventsItem>>()
    val finishedEvents: LiveData<List<ListEventsItem>> = _finishedEvents
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackbarText: LiveData<Event<String>> = _snackbarText

    // Fungsi untuk mengambil Upcoming Events
    fun fetchUpcomingEvents() {
        _isLoading.value = true
        ApiConfig.getApiService().getUpcomingEvents().enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                _isLoading.value = false // Pastikan loading dinonaktifkan
                if (response.isSuccessful) {
                    handleUpcomingEventsResponse(response.body())
                } else {
                    handleError("Gagal mengambil upcoming events")
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _isLoading.value = false // Pastikan loading dinonaktifkan
                handleError("Tidak ada internet: ${t.message}")
            }
        })
    }

    // Fungsi untuk mengambil Finished Events
    fun fetchFinishedEvents() {
        _isLoading.value = true
        ApiConfig.getApiService().getFinishedEvents().enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                _isLoading.value = false // Pastikan loading dinonaktifkan
                if (response.isSuccessful) {
                    handleFinishedEventsResponse(response.body())
                } else {
                    handleError("Gagal mengambil finished events")
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _isLoading.value = false // Pastikan loading dinonaktifkan
                handleError("Tidak ada internet: ${t.message}")
            }
        })
    }

    private fun handleUpcomingEventsResponse(eventResponse: EventResponse?) {
        val events = eventResponse?.listEvents ?: emptyList()
        _upcomingEvents.value = events.take(5)
    }

    private fun handleFinishedEventsResponse(eventResponse: EventResponse?) {
        val events = eventResponse?.listEvents ?: emptyList()
        _finishedEvents.value = events.take(5)
    }

    private fun handleError(message: String) {
        _snackbarText.value = Event(message)
    }
}
