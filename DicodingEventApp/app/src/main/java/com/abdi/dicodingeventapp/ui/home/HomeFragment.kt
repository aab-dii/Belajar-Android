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
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
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

        binding.rvUpcoming.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        binding.rvUpcoming.adapter = upcomingEventsAdapter

        binding.rvFinished.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFinished.adapter = finishedEventsAdapter

        viewModel.upcomingEvents.observe(viewLifecycleOwner) { upcomingEvents ->
            upcomingEventsAdapter.submitList(upcomingEvents)
        }

        viewModel.finishedEvents.observe(viewLifecycleOwner) { finishedEvents ->
            finishedEventsAdapter.submitList(finishedEvents)
        }

        viewModel.isLoading.observe(viewLifecycleOwner){ isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
        viewModel.snackbarText.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let{ snackBarText ->
                Snackbar.make(
                    binding.root,
                    snackBarText,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        })
        viewModel.fetchUpcomingEvents()
        viewModel.fetchFinishedEvents()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
