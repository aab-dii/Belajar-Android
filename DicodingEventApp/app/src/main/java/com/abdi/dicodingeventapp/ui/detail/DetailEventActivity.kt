package com.abdi.dicodingeventapp.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.abdi.dicodingeventapp.databinding.ActivityDetailEventBinding
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar

class DetailEventActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailEventBinding
    private val detailViewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

       detailViewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        detailViewModel.snackbarText.observe(this, {
            it.getContentIfNotHandled()?.let{ snackBarText ->
                Snackbar.make(
                    binding.root,
                    snackBarText,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        })
        setSupportActionBar(binding.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        val eventId = intent.getIntExtra("id", -1)

        detailViewModel.setID(eventId)

        detailViewModel.eventDetail.observe(this) { eventDetail ->
            if (eventDetail != null && !eventDetail.error) {  // Memastikan tidak ada error
                binding.tvTitle.text = eventDetail.event.name
                supportActionBar?.title = eventDetail.event.name
                Glide.with(this)
                    .load(eventDetail.event.mediaCover)
                    .into(binding.imgEvent)
                val quota = eventDetail.event.quota - eventDetail.event.registrants
                binding.tvQuota.text = quota.toString()
                binding.tvDescription.text = HtmlCompat.fromHtml(eventDetail.event.description, HtmlCompat.FROM_HTML_MODE_LEGACY)
                binding.tvOwner.text = eventDetail.event.ownerName
                binding.tvBeginTime.text = eventDetail.event.beginTime
                binding.btnRegister.setOnClickListener {
                    val url = eventDetail.event.link
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(intent)
                }
            }
        }
    }
}