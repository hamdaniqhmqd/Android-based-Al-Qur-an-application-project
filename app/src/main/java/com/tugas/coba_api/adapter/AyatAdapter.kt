package com.tugas.coba_api.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tugas.coba_api.R
import com.tugas.coba_api.data.al_quran.Ayat

class AyatAdapter(private val ayatList: List<Ayat>) : RecyclerView.Adapter<AyatAdapter.AyatViewHolder>() {

    class AyatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvArabic: TextView = itemView.findViewById(R.id.NamaLatinAyat)
        val tvTranslation: TextView = itemView.findViewById(R.id.ArtiAyat)
        val tvNomor: TextView = itemView.findViewById(R.id.number)
        val ivNomor: ImageView = itemView.findViewById(R.id.image_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AyatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ayat, parent, false)
        return AyatViewHolder(view)
    }

    override fun onBindViewHolder(holder: AyatViewHolder, position: Int) {
        val ayat = ayatList[position]
        holder.tvArabic.text = ayat.ar
        holder.tvTranslation.text = ayat.idn
        holder.tvNomor.text = ayat.nomor.toString()

        Glide.with(holder.itemView.context)
            .load("https://www.pinclipart.com/picdir/big/36-364719_nomor-ayat-al-quran-clipart.png")
            .into(holder.ivNomor)
    }

    override fun getItemCount(): Int = ayatList.size
}
