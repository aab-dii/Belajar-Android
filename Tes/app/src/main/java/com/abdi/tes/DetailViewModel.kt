package com.abdi.tes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {
    private val _eventDetail = MutableLiveData<ResponseDetail>()
    val eventDetail: LiveData<ResponseDetail> = _eventDetail
    private var eventId: Int = 0

    fun setID(id: Int) {
        eventId = id
        getEventDetail(eventId)
    }

    private fun getEventDetail(eventId: Int) {
        val client = ApiConfig.getApiService().getEventsDetail(eventId)
        client.enqueue(object : Callback<ResponseDetail> {
            override fun onResponse(
                call: Call<ResponseDetail>,
                response: Response<ResponseDetail>
            ) {
                if (response.isSuccessful) {
                    val eventDetail = response.body()
                    if (eventDetail != null) {
                        _eventDetail.value = eventDetail
                    } else {
                        Log.e("DetailViewModel", "Data event kosong")
                    }
                } else {
                    Log.e("DetailViewModel", "Response tidak berhasil: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseDetail>, t: Throwable) {
                Log.e("DetailViewModel", "Gagal mengambil data: ${t.message}")
            }
        })
    }
}
