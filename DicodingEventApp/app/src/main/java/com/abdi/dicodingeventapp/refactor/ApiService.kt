package com.abdi.dicodingeventapp.refactor

import com.abdi.dicodingeventapp.response.DetailEventResponse
import com.abdi.dicodingeventapp.response.EventResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("events?active=0")
    fun getEvents(): Call<EventResponse>

    @GET("events/{id}")
    fun getDetailEvent(@Path("id") id: String): Call<DetailEventResponse>
}