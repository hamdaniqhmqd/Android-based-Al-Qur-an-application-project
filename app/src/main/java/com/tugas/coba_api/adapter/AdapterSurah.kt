package com.tugas.coba_api.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tugas.coba_api.data.al_quran.AlQuranResponse
import com.tugas.coba_api.databinding.ItemAlQuranBinding

class AdapterSurah(
    private val onClick: (AlQuranResponse) -> Unit
) : ListAdapter<AlQuranResponse, AdapterSurah.SurahViewHolder>(SurahDiffCallback()) {

    class SurahViewHolder(val binding: ItemAlQuranBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SurahViewHolder {
        val binding = ItemAlQuranBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SurahViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SurahViewHolder, position: Int) {
        val data = getItem(position)

        holder.binding.apply {
            number.text = data.nomor.toString()
            NamaSurah.text = data.nama_latin
            ArtiSurah.text = data.arti
            NamaLatinSurah.text = data.nama
            BanyakAyat.text = "${data.jumlah_ayat} Ayat"

            // Load gambar menggunakan Glide
            Glide.with(holder.binding.imageView.context)
                .load("https://www.pinclipart.com/picdir/big/36-364719_nomor-ayat-al-quran-clipart.png")
                .into(holder.binding.imageView)

            // Set onClick listener
            root.setOnClickListener {
                onClick(data)
            }
        }
    }

    class SurahDiffCallback : DiffUtil.ItemCallback<AlQuranResponse>() {
        override fun areItemsTheSame(oldItem: AlQuranResponse, newItem: AlQuranResponse): Boolean {
            return oldItem.nomor == newItem.nomor
        }

        override fun areContentsTheSame(oldItem: AlQuranResponse, newItem: AlQuranResponse): Boolean {
            return oldItem == newItem
        }
    }
}
