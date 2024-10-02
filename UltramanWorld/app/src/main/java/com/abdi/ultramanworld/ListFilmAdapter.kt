package com.abdi.ultramanworld

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ListFilmAdapter(
    private val context: Context,
    private val listFilm: ArrayList<Film>,
) : RecyclerView.Adapter<ListFilmAdapter.ListViewHolder>() {

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
        val tvName: TextView = itemView.findViewById(R.id.tv_item_name)
        val tvRating: TextView = itemView.findViewById(R.id.tv_item_rating)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_grid_films, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        // Deklarasikan film dari listFilm
        val film = listFilm[position]
        // Gunakan Glide untuk memuat gambar
        Glide.with(holder.itemView.context)
            .load(film.photo)
            .into(holder.imgPhoto)
        holder.tvName.text = film.name
        holder.tvRating.text = film.rating
        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java).apply {
                putExtra(DetailActivity.EXTRA_NAME, film.name) // Gunakan film.name
                putExtra(DetailActivity.EXTRA_RATING, film.rating) // Gunakan film.rating
                putExtra(DetailActivity.EXTRA_YEAR, film.year)
                putExtra(DetailActivity.EXTRA_DESCRIPTION, film.description) // Masukkan deskripsi
                putExtra(DetailActivity.EXTRA_EPISODE, film.episode)
                putExtra(DetailActivity.EXTRA_PHOTO, film.photo) // Gunakan film.photo
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = listFilm.size
}
