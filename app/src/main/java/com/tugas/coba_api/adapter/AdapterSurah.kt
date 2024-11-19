package com.tugas.coba_api.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tugas.coba_api.data.al_quran.AlQuranResponse
import com.tugas.coba_api.databinding.ItemAlQuranBinding

class AdapterSurah :
    ListAdapter<AlQuranResponse, AdapterSurah.SurahViewHolder>(PokemonDiffCallback()) {

    class SurahViewHolder(val binding: ItemAlQuranBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SurahViewHolder {
        val binding = ItemAlQuranBinding.inflate(LayoutInflater.from(parent.context))
        return SurahViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SurahViewHolder, position: Int) {
        val data = getItem(position)

        holder.binding.number.text = data.nomor.toString()
        holder.binding.NamaSurah.text = data.nama_latin
        holder.binding.ArtiSurah.text = data.arti
        holder.binding.NamaLatinSurah.text = data.nama
        holder.binding.BanyakAyat.text = "${data.jumlah_ayat} Ayat"
    }


    class PokemonDiffCallback : DiffUtil.ItemCallback<AlQuranResponse>() {
        override fun areItemsTheSame(oldItem: AlQuranResponse, newItem: AlQuranResponse): Boolean {
            return oldItem.nomor == newItem.nomor
        }

        override fun areContentsTheSame(oldItem: AlQuranResponse, newItem: AlQuranResponse): Boolean {
            return oldItem == newItem
        }
    }
}