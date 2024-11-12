package com.abdi.dicodingeventapp.data.remote.retrofit

import com.abdi.dicodingeventapp.data.remote.response.DetailEventResponse
import com.abdi.dicodingeventapp.data.remote.response.EventResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("events/{id}")
    suspend fun getDetailEvent(@Path("id") id: Int
    ): DetailEventResponse

    @GET("events")
    fun getEvents(
        @Query("active") active: Int
    ): Call<EventResponse>

    @GET("events")
    fun getHomeEvents(
        @Query("active") active: Int,
        @Query("limit") limit: Int = 5
    ): Call<EventResponse>


    @GET("events")
    fun searchEvents(
        @Query("active") active: Int,
        @Query("q") keyword: String
    ): Call<EventResponse>
}