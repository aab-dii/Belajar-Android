package com.abdi.dicodingeventapp.di

import android.content.Context
import com.abdi.dicodingeventapp.data.local.EventRepository
import com.abdi.dicodingeventapp.data.local.room.EventDatabase
import com.abdi.dicodingeventapp.data.remote.retrofit.ApiConfig
import com.abdi.dicodingeventapp.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): EventRepository {
        val apiService = ApiConfig.getApiService()
        val database = EventDatabase.getInstance(context.applicationContext)
        val dao = database.eventDao()
        val appExecutors = AppExecutors()
        return EventRepository.getInstance(apiService, dao, appExecutors)
    }
}