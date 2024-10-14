package com.abdi.dicodingeventapp.ui.finished

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

class FinishedViewModel : ViewModel() {

    private val _events = MutableLiveData<List<ListEventsItem>>()
    val events: LiveData<List<ListEventsItem>> = _events
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackbarText: LiveData<Event<String>> = _snackbarText

    init {
        fetchEvents()
    }

    fun fetchEvents() {
        _isLoading.value = true
        ApiConfig.getApiService().getFinishedEvents().enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    handleSuccessResponse(response.body())
                } else {
                    handleError("Gagal menampilkan event")
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _isLoading.value = false
                handleError("Tidak ada koneksi internet")
            }
        })
    }

    fun searchEvents(keyword: String) {
        _isLoading.value = true
        ApiConfig.getApiService().searchEvents(active = 0, keyword = keyword).enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    handleSuccessResponse(response.body())
                } else {
                    handleError("Gagal menampilkan event")
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _isLoading.value = false
                handleError("Tidak ada koneksi internet")
            }
        })
    }

    private fun handleSuccessResponse(eventResponse: EventResponse?) {
        _events.value = eventResponse?.listEvents ?: emptyList()
    }

    private fun handleError(message: String) {
        _snackbarText.value = Event(message)
    }
}