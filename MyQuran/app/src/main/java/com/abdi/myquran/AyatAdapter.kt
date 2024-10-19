package com.abdi.myquran

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AyatAdapter(private val ayatList: List<AyatItem>) : RecyclerView.Adapter<AyatAdapter.AyatViewHolder>() {

    class AyatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val teksArab: TextView = itemView.findViewById(R.id.teksArab)
        val teksLatin: TextView = itemView.findViewById(R.id.teksLatin)
        val teksIndonesia: TextView = itemView.findViewById(R.id.teksIndonesia)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AyatViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_detail, parent, false)
        return AyatViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AyatViewHolder, position: Int) {
        val currentAyat = ayatList[position]
        holder.teksArab.text = currentAyat.teksArab
        holder.teksLatin.text = currentAyat.teksLatin
        holder.teksIndonesia.text = currentAyat.teksIndonesia
    }

    override fun getItemCount() = ayatList.size
}
