package com.abdi.dicodingeventapp.ui.finished

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abdi.dicodingeventapp.data.local.EventRepository
import com.abdi.dicodingeventapp.data.remote.response.ListEventsItem
import com.abdi.dicodingeventapp.data.local.Result

class FinishedViewModel(private val eventRepository: EventRepository) : ViewModel() {


    private val _events = MutableLiveData<Result<List<ListEventsItem>>>()
    val events: LiveData<Result<List<ListEventsItem>>> get() = _events

    fun getFinishedEvents()= eventRepository.fetchFromApi(0)

    private val _searchResults = MutableLiveData<Result<List<ListEventsItem>>>()
    val searchResults: LiveData<Result<List<ListEventsItem>>> get() = _searchResults

    fun searchEvent(name: String) {
        eventRepository.searchEvent(0, name).observeForever { result ->
            _searchResults.value = result
        }
    }



}