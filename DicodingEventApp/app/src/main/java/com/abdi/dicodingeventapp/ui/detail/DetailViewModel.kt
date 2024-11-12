package com.abdi.dicodingeventapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.abdi.dicodingeventapp.data.local.EventRepository
import com.abdi.dicodingeventapp.data.remote.response.DetailEventResponse
import com.abdi.dicodingeventapp.data.local.Result
import com.abdi.dicodingeventapp.data.local.entity.EventEntity
import kotlinx.coroutines.launch

class DetailViewModel(private val eventRepository: EventRepository) : ViewModel() {

    private var eventId: Int = 0
    fun setID(id: Int) {
        eventId = id
    }

    fun getEventDetail(): LiveData<Result<DetailEventResponse>> = liveData {
        emit(Result.Loading)
        try {
            val event = eventRepository.getEventDetail(eventId)
            emit(Result.Success(event))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun saveFavEvent(eventEntity: EventEntity) {
        viewModelScope.launch {
            eventRepository.saveFavoriteEvent(eventEntity)
        }
    }

    fun deleteFavEvent(eventId: Int) {
        viewModelScope.launch {
            eventRepository.deleteFavoriteEvent(eventId)
        }
    }

    fun isEventFavorite(eventId: Int): LiveData<Boolean> {
        return eventRepository.isEventFavorite(eventId)
    }

}
