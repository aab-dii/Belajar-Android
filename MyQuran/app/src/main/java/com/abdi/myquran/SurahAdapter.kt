package com.abdi.myquran

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SurahAdapter(private val surahList: List<DataItem>) : RecyclerView.Adapter<SurahAdapter.SurahViewHolder>() {

    class SurahViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewSurahName: TextView = itemView.findViewById(R.id.textViewSurahName)
        val surahLatin: TextView = itemView.findViewById(R.id.surah_latin)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SurahViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_surah, parent, false)
        return SurahViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SurahViewHolder, position: Int) {
        val currentSurah = surahList[position]
        holder.textViewSurahName.text = currentSurah.nama
        holder.surahLatin.text = currentSurah.namaLatin

        // Set OnClickListener pada itemView untuk menangani klik
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetailActivity::class.java).apply {
                putExtra("nomor", currentSurah.nomor) // Mengirimkan nomor surah
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = surahList.size
}


