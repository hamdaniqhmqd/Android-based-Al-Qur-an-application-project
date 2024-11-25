package com.tugas.coba_api.data.al_quran

import com.google.gson.annotations.SerializedName

data class DetailSurah(
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("nomor")
    val nomor: Int,
    @SerializedName("nama")
    val nama: String,
    @SerializedName("nama_latin")
    val nama_latin: String,
    @SerializedName("jumlah_ayat")
    val jumlah_ayat: Int,
    @SerializedName("tempat_turun")
    val tempat_turun: String,
    @SerializedName("arti")
    val arti: String,
    @SerializedName("deskripsi")
    val deskripsi: String,
    @SerializedName("audio")
    val audio: String,
    @SerializedName("ayat")
    val ayat: List<Ayat>,
    @SerializedName("surat_selanjutnya")
    val surat_selanjutnya: SuratSelanjutnya? = null,
    @SerializedName("surat_sebelumnya")
    val surat_sebelumnya: SuratSelanjutnya? = null
)

data class Ayat(
    val id: Int,
    val surah: Int,
    val nomor: Int,
    val ar: String,
    val tr: String,
    val idn: String
)

data class SuratSelanjutnya(
    val id: Int,
    val nomor: Int,
    val nama: String,
    val namaLatin: String,
    val jumlahAyat: Int,
    val tempatTurun: String,
    val arti: String,
    val deskripsi: String,
    val audio: String
)

