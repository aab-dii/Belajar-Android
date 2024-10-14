package com.abdi.dicodingeventapp.ui.finished

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdi.dicodingeventapp.databinding.FragmentFinishedBinding
import com.abdi.dicodingeventapp.ui.EventsAdapter
import com.google.android.material.snackbar.Snackbar

class FinishedFragment : Fragment() {
    private var _binding: FragmentFinishedBinding? = null
    private val binding get() = _binding!!
    private lateinit var eventAdapter: EventsAdapter
    private val viewModel: FinishedViewModel by viewModels()
    private val handler = Handler(Looper.getMainLooper())
    private var searchRunnable: Runnable? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        eventAdapter = EventsAdapter(isUpcoming = false)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = eventAdapter

        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Batalkan runnable sebelumnya jika ada
                searchRunnable?.let { handler.removeCallbacks(it) }

                if (!newText.isNullOrEmpty()) {
                    // Buat runnable untuk pencarian dengan delay
                    searchRunnable = Runnable {

                        viewModel.searchEvents(newText) // Panggil fungsi pencarian
                    }
                    showLoading()
                    handler.postDelayed(searchRunnable!!, 1000)
                } else {
                    // Jika teks pencarian kosong, ambil data awal
                    showLoading()
                    viewModel.fetchEvents()
                }
                return true
            }
        })

        viewModel.events.observe(viewLifecycleOwner) { events ->
            eventAdapter.submitList(events)
        }
        showLoading()
        viewModel.snackbarText.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let{ snackBarText ->
                Snackbar.make(
                    binding.root,
                    snackBarText,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        })
        viewModel.fetchEvents()
    }
    private fun showLoading(){
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        searchRunnable?.let { handler.removeCallbacks(it) }
        _binding = null
    }
}
