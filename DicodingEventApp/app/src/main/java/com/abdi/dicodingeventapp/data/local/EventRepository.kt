package com.abdi.dicodingeventapp.data.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abdi.dicodingeventapp.data.local.entity.EventEntity
import com.abdi.dicodingeventapp.data.local.room.EventDao
import com.abdi.dicodingeventapp.data.remote.response.DetailEventResponse
import com.abdi.dicodingeventapp.data.remote.retrofit.ApiService
import com.abdi.dicodingeventapp.data.remote.response.EventResponse
import com.abdi.dicodingeventapp.data.remote.response.ListEventsItem
import com.abdi.dicodingeventapp.utils.AppExecutors
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventRepository (
    private val apiService: ApiService,
    private val eventDao: EventDao,
    private val appExecutors: AppExecutors
) {

    fun fetchFromApi(active: Int): LiveData<Result<List<ListEventsItem>>> {
        val result = MutableLiveData<Result<List<ListEventsItem>>>()
        result.value = Result.Loading

        val client = apiService.getEvents(active)
        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                if (response.isSuccessful) {
                    val events = response.body()?.listEvents ?: emptyList()
                    result.value = Result.Success(events)
                } else {
                    result.value = Result.Error("Response not successful: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                result.value = Result.Error("Tidak ada koneksi internet")
            }
        })

        return result
    }

    fun fetchFromHomeApi(active: Int): LiveData<Result<List<ListEventsItem>>> {
        val result = MutableLiveData<Result<List<ListEventsItem>>>()
        result.value = Result.Loading

        val client = apiService.getHomeEvents(active)
        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                if (response.isSuccessful) {
                    val events = response.body()?.listEvents ?: emptyList()
                    result.value = Result.Success(events)
                } else {
                    result.value = Result.Error("Response not successful: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                result.value = Result.Error("Tidak ada koneksi internet")
            }
        })

        return result
    }

    suspend fun getEventDetail(eventId: Int): DetailEventResponse {
        return apiService.getDetailEvent(eventId)
    }

    fun saveFavoriteEvent(event: EventEntity) {
        appExecutors.diskIO.execute {
            eventDao.insertEvents(event)
        }
    }

    fun deleteFavoriteEvent(id: Int) {
        appExecutors.diskIO.execute {
            eventDao.deleteEvent(id)
        }
    }

    fun searchEvent(active: Int, name: String) : LiveData<Result<List<ListEventsItem>>> {
        val result = MutableLiveData<Result<List<ListEventsItem>>>()
        result.value = Result.Loading

        val client = apiService.searchEvents(active, name)
        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                if (response.isSuccessful) {
                    val events = response.body()?.listEvents ?: emptyList()
                    result.value = Result.Success(events)
                } else {
                    result.value = Result.Error("Response not successful: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                result.value = Result.Error("Tidak ada koneksi internet")
            }
        })

        return result
    }

    fun searchFavoriteEventByName(name: String): List<EventEntity> {
        val results = eventDao.searchFavoriteEventByName("%$name%")
        return results
    }

    fun getFavoriteEvents(): LiveData<List<EventEntity>> {
        return eventDao.getFavoriteEvents()
    }

    fun isEventFavorite(eventId: Int): LiveData<Boolean> {
        return eventDao.isEventFavorite(eventId)
    }

    companion object {
        @Volatile
        private var instance: EventRepository? = null
        fun getInstance(
            apiService: ApiService,
            eventDao: EventDao,
            appExecutors: AppExecutors
        ): EventRepository =
            instance ?: synchronized(this) {
                instance ?: EventRepository(apiService, eventDao, appExecutors)
            }.also { instance = it }
    }
}
