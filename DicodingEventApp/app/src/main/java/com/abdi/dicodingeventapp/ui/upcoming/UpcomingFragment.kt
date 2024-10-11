package com.abdi.dicodingeventapp.ui.upcoming

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdi.dicodingeventapp.databinding.FragmentUpcomingBinding
import com.abdi.dicodingeventapp.response.DetailEventResponse
import com.abdi.dicodingeventapp.ui.EventsAdapter
import com.abdi.dicodingeventapp.ui.detail.DetailEventActivity

class UpcomingFragment : Fragment(){

    private lateinit var binding: FragmentUpcomingBinding
    private val viewModel: UpcomingViewModel by viewModels()
    private lateinit var eventAdapter: EventsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpcomingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inisialisasi RecyclerView
        eventAdapter = EventsAdapter { eventItem: DetailEventResponse -> // Ubah menjadi ListEventsItem, bukan DetailEventResponse
            openEventDetail(eventItem) // Meneruskan ListEventsItem ke fungsi openEventDetail
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = eventAdapter

        // Amati LiveData dari ViewModel
        viewModel.events.observe(viewLifecycleOwner) { events ->
            eventAdapter.submitList(events) // Memasukkan data event ke adapter
        }

        // Panggil fetchEvents() untuk memulai pengambilan data
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


//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
}
