package com.abdi.dicodingeventapp.ui.upcoming

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abdi.dicodingeventapp.data.local.EventRepository
import com.abdi.dicodingeventapp.data.local.Result
import com.abdi.dicodingeventapp.data.remote.response.ListEventsItem

class UpcomingViewModel(private val eventRepository: EventRepository) : ViewModel() {

    fun getUpcomingEvent()= eventRepository.fetchFromApi(1)

    private val _searchResults = MutableLiveData<Result<List<ListEventsItem>>>()
    val searchResults: LiveData<Result<List<ListEventsItem>>> get() = _searchResults

    fun searchEvent(name: String) {
        eventRepository.searchEvent(1, name).observeForever { result ->
            _searchResults.value = result
        }
    }
}

