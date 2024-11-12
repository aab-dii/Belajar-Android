package com.abdi.dicodingeventapp.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdi.dicodingeventapp.data.local.EventRepository
import com.abdi.dicodingeventapp.data.local.entity.EventEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(private val eventRepository: EventRepository) : ViewModel() {
    private val _favoriteEvents = MutableLiveData<List<EventEntity>>()
    val favoriteEvents: LiveData<List<EventEntity>> get() = _favoriteEvents

    private val _searchResults = MutableLiveData<List<EventEntity>>()
    val searchResults: LiveData<List<EventEntity>> get() = _searchResults

    init {
        loadFavoriteEvents()
    }

    private fun loadFavoriteEvents() {
        eventRepository.getFavoriteEvents().observeForever { events ->
            _favoriteEvents.postValue(events)
        }
    }

    fun searchEvent(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val results = eventRepository.searchFavoriteEventByName(query)
            _searchResults.postValue(results)
        }
    }
}
