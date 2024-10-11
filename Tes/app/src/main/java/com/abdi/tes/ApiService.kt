package com.abdi.tes

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("events")
    fun getEvents(): Call<EventResponse>

    @GET("events/{id}")
    fun getEventsDetail(@Path("id") id: Int): Call<EventDetail>
    // Menerima ID
}
