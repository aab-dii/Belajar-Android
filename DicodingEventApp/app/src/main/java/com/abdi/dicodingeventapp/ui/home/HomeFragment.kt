package com.abdi.dicodingeventapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdi.dicodingeventapp.databinding.FragmentHomeBinding
import com.abdi.dicodingeventapp.ui.EventsAdapter
import com.abdi.dicodingeventapp.ui.ViewModelFactory
import com.abdi.dicodingeventapp.data.local.Result
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var upcomingEventsAdapter: EventsAdapter
    private lateinit var finishedEventsAdapter: EventsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        upcomingEventsAdapter = EventsAdapter(isUpcoming = true)
        finishedEventsAdapter = EventsAdapter(isUpcoming = false)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val viewModel: HomeViewModel by viewModels {
            factory
        }
        binding.rvUpcoming.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvUpcoming.adapter = upcomingEventsAdapter

        binding.rvFinished.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFinished.adapter = finishedEventsAdapter

        viewModel.getUpcomingEvent().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBarUpcoming.visibility = View.VISIBLE
                    binding.rvUpcoming.visibility = View.GONE
                }
                is Result.Success -> {
                    binding.progressBarUpcoming.visibility = View.GONE
                    binding.rvUpcoming.visibility = View.VISIBLE
                    val eventData = result.data
                    upcomingEventsAdapter.submitList(eventData)
                }
                is Result.Error -> {
                    binding.progressBarUpcoming.visibility = View.GONE
                    Snackbar.make(binding.root, "Terjadi kesalahan: ${result.error}", Snackbar.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.getFinishedEvent().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBarFinished.visibility = View.VISIBLE
                    binding.rvFinished.visibility = View.GONE
                }
                is Result.Success -> {
                    binding.progressBarFinished.visibility = View.GONE
                    binding.rvFinished.visibility = View.VISIBLE
                    val eventData = result.data
                    finishedEventsAdapter.submitList(eventData)
                }
                is Result.Error -> {
                    binding.progressBarFinished.visibility = View.GONE
                    Snackbar.make(binding.root, "Terjadi kesalahan: ${result.error}", Snackbar.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.getUpcomingEvent()
        viewModel.getFinishedEvent()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
