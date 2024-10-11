package com.abdi.dicodingeventapp.ui.finished

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdi.dicodingeventapp.databinding.FragmentFinishedBinding
import com.abdi.dicodingeventapp.response.DetailEventResponse
import com.abdi.dicodingeventapp.response.ListEventsItem
import com.abdi.dicodingeventapp.ui.EventsAdapter
import com.abdi.dicodingeventapp.ui.detail.DetailEventActivity
import com.abdi.dicodingeventapp.ui.upcoming.UpcomingViewModel

class FinishedFragment : Fragment() { // Implementasi listener
    private lateinit var eventAdapter: EventsAdapter
    private lateinit var binding: FragmentFinishedBinding
    private val viewModel: FinishedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        eventAdapter = EventsAdapter { eventItem: DetailEventResponse ->
            openEventDetail(eventItem) // Meneruskan ListEventsItem ke fungsi openEventDetail
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = eventAdapter

        viewModel.events.observe(viewLifecycleOwner) { events ->
            eventAdapter.submitList(events) // Memasukkan data event ke adapter
        }
        // Memanggil fetch data
        viewModel.fetchEvents()
    }

    private fun openEventDetail(eventDetail: DetailEventResponse) {
        val intent = Intent(requireContext(), DetailEventActivity::class.java).apply {
            putExtra(DetailEventActivity.EXTRA_NAME, eventDetail.name) // Nama event
            putExtra(DetailEventActivity.EXTRA_PHOTO, eventDetail.mediaCover) // Gambar event
            putExtra(DetailEventActivity.EXTRA_OWNER, eventDetail.ownerName) // Penyelenggara acara
            putExtra(DetailEventActivity.EXTRA_BEGIN_TIME, eventDetail.beginTime) // Waktu mulai acara
            putExtra(DetailEventActivity.EXTRA_QUOTA, eventDetail.quota) // Kuota acara
            putExtra(DetailEventActivity.EXTRA_REGISTRANTS, eventDetail.registrants) // Jumlah registran
            putExtra(DetailEventActivity.EXTRA_DESCRIPTION, eventDetail.description) // Deskripsi acara
        }
        startActivity(intent)
    }

}
