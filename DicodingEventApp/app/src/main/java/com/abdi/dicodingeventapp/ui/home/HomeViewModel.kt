package com.abdi.dicodingeventapp.ui.home

import androidx.lifecycle.ViewModel
import com.abdi.dicodingeventapp.data.local.EventRepository

class HomeViewModel(private val eventRepository: EventRepository) : ViewModel() {

    init {
        getUpcomingEvent()
        getFinishedEvent()
    }

    fun getUpcomingEvent()= eventRepository.fetchFromHomeApi(1)
    fun getFinishedEvent()= eventRepository.fetchFromHomeApi(0)
}

