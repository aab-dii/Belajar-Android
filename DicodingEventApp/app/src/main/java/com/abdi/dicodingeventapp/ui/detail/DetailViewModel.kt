package com.abdi.dicodingeventapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abdi.dicodingeventapp.data.response.DetailEventResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.abdi.dicodingeventapp.data.refactor.ApiConfig
import com.abdi.dicodingeventapp.utils.Event

class DetailViewModel : ViewModel() {

    private val _eventDetail = MutableLiveData<DetailEventResponse?>()
    val eventDetail: LiveData<DetailEventResponse?> = _eventDetail
    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackbarText: LiveData<Event<String>> = _snackbarText
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var eventId: Int = 0

    fun setID(id: Int) {
        eventId = id
        getEventDetail(eventId)
    }

    private fun getEventDetail(eventId: Int) {
        _isLoading.value = true
        ApiConfig.getApiService().getDetailEvent(eventId)
            .enqueue(object : Callback<DetailEventResponse> {
            override fun onResponse(
                call: Call<DetailEventResponse>,
                response: Response<DetailEventResponse>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    val eventDetail = response.body()
                    if (eventDetail != null) {
                        _eventDetail.value = eventDetail
                    }
                } else {
                    _isLoading.value = false
                    _snackbarText.value = Event("Data event kosong")
                }
            }

            override fun onFailure(call: Call<DetailEventResponse>, t: Throwable) {
                _isLoading.value = false
                _snackbarText.value = Event("Tidak ada koneksi internet")
            }
        })
    }
}
