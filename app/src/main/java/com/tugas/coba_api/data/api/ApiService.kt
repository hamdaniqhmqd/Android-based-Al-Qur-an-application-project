package com.tugas.coba_api.data.api

import com.tugas.coba_api.data.al_quran.AlQuranResponse
import com.tugas.coba_api.data.al_quran.DetailSurah
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface ApiService {
    @GET("/api/surah")
    suspend fun getSurah(): Response<List<AlQuranResponse>>

    @GET("api/surah/{nomor}")
    suspend fun getSurahByNomor(@Path("nomor") nomor: Int): Response<DetailSurah>

}
