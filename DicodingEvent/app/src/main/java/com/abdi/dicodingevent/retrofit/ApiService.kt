package com.abdi.dicodingevent.retrofit

import com.abdi.dicodingevent.response.EventResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("events?active=0")
    fun getEvents(): Call<EventResponse>
}
