package com.abdi.myquran

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SurahAdapter(private val surahList: List<DataItem>) : RecyclerView.Adapter<SurahAdapter.SurahViewHolder>() {

    class SurahViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewSurahName: TextView = itemView.findViewById(R.id.textViewSurahName)
        // Tambahkan view lain jika perlu
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SurahViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_surah, parent, false)
        return SurahViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SurahViewHolder, position: Int) {
        val currentSurah = surahList[position]
        holder.textViewSurahName.text = currentSurah.nama
    }

    override fun getItemCount() = surahList.size
}

