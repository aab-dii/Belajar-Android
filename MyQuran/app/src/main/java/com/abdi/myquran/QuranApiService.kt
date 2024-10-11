package com.abdi.myquran

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface QuranApiService {
    @GET("surat")
    @Headers("Accept: application/json", "Content-Type: application/json")
    fun getSurahList(): Call<SurahResponse> // Menggunakan SurahResponse
}



