package com.tugas.coba_api.data.al_quran

import com.google.gson.annotations.SerializedName

data class AlQuranResponse (
    @SerializedName("nomor"       ) var nomor       : Int?     = null,
    @SerializedName("nama"     ) var nama     : String?  = null,
    @SerializedName("nama_latin" ) var nama_latin : String?  = null,
    @SerializedName("jumlah_ayat" ) var jumlah_ayat : Int?  = null,
    @SerializedName("tempat_turun" ) var tempat_turun : String?  = null,
    @SerializedName("arti" ) var arti : String?  = null,
    @SerializedName("deskripsi" ) var deskripsi : String?  = null,
    @SerializedName("audio" ) var audio : String?  = null,
)