package com.abdi.dicodingeventapp.data.refactor

import com.abdi.dicodingeventapp.data.response.DetailEventResponse
import com.abdi.dicodingeventapp.data.response.EventResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("events")
    fun getFinishedEvents(
        @Query("active") active: Int = 0
    ): Call<EventResponse>

    @GET("events/{id}")
    fun getDetailEvent(@Path("id") id: Int
    ): Call<DetailEventResponse>

    @GET("events")
    fun getUpcomingEvents(
        @Query("active") active: Int = 1
    ): Call<EventResponse>

    @GET("events")
    fun searchEvents(
        @Query("active") active: Int = -1,
        @Query("q") keyword: String
    ): Call<EventResponse>
}