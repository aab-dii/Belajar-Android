package com.abdi.dicodingeventapp.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.abdi.dicodingeventapp.R
import com.abdi.dicodingeventapp.data.remote.response.DetailEventResponse
import com.abdi.dicodingeventapp.databinding.ActivityDetailEventBinding
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.abdi.dicodingeventapp.data.local.Result
import com.abdi.dicodingeventapp.data.local.entity.EventEntity

class DetailEventActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailEventBinding
    private val detailViewModel: DetailViewModel by viewModels {
        DetailViewModelFactory.getInstance(this)
    }

    private lateinit var mediaCoverURL : String
    private var eventId: Int = 0
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        eventId = intent.getIntExtra("EVENT_ID", -1)
        if (eventId != -1) {
            detailViewModel.setID(eventId)
            observeEventDetail()
            checkIfFavorite()
        } else {
            Snackbar.make(binding.root, "Event ID tidak valid", Snackbar.LENGTH_SHORT).show()
        }

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        binding.btnFavorite.setOnClickListener {
            if (isFavorite) {
                removeFavorite()
            } else {
                addFavorite()
            }
        }
    }

    private fun observeEventDetail() {
        detailViewModel.getEventDetail().observe(this) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.btnFavorite.visibility = View.GONE
                    binding.btnRegister.visibility = View.GONE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    val eventDetail = result.data
                    updateUI(eventDetail)
                    binding.btnFavorite.visibility = View.VISIBLE
                    binding.btnRegister.visibility = View.VISIBLE
                }
                is Result.Error -> {
                    binding.btnFavorite.visibility = View.GONE
                    binding.btnRegister.visibility = View.GONE
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }

    private fun updateUI(eventDetail: DetailEventResponse) {
        supportActionBar?.title = eventDetail.event.name
        binding.tvTitle.text = eventDetail.event.name
        binding.tvQuota.text = (eventDetail.event.quota - eventDetail.event.registrants).toString()
        binding.tvDescription.text = HtmlCompat.fromHtml(eventDetail.event.description, HtmlCompat.FROM_HTML_MODE_LEGACY)
        binding.tvOwner.text = eventDetail.event.ownerName
        binding.tvBeginTime.text = eventDetail.event.beginTime
        mediaCoverURL = eventDetail.event.mediaCover

        Glide.with(this)
            .load(eventDetail.event.mediaCover)
            .into(binding.imgEvent).toString()

        detailViewModel.isEventFavorite(eventId).observe(this) { isFav ->
            isFavorite = isFav
            updateFavoriteIcon(isFavorite)
        }

        binding.btnRegister.setOnClickListener {
            val url = eventDetail.event.link
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        }
    }

    private fun updateFavoriteIcon(isFavorite: Boolean) {
        if (isFavorite) {
            binding.btnFavorite.setImageResource(R.drawable.ic_favorite)
        } else {
            binding.btnFavorite.setImageResource(R.drawable.ic_favorite_border)
        }
    }

    private fun addFavorite() {
        val event = EventEntity(
            id = eventId,
            name = binding.tvTitle.text.toString(),
            mediaCover = mediaCoverURL,
            isFavorite = true
        )
        detailViewModel.saveFavEvent(event)
        updateFavoriteIcon(true)
        Snackbar.make(binding.root, "Ditambahkan ke favorit", Snackbar.LENGTH_SHORT).show()
    }

    private fun removeFavorite() {
        detailViewModel.deleteFavEvent(eventId)
        updateFavoriteIcon(false)
        Snackbar.make(binding.root, "Dihapus dari favorit", Snackbar.LENGTH_SHORT).show()
    }

    private fun checkIfFavorite() {
        detailViewModel.isEventFavorite(eventId).observe(this) { isFav ->
            isFavorite = isFav
            updateFavoriteIcon(isFavorite)
        }
    }
}
