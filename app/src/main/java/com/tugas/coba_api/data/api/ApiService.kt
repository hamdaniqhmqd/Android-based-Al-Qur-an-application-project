package com.tugas.coba_api.data.api

import com.tugas.coba_api.data.al_quran.AlQuranResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface ApiService {
    @GET("/api/surah")
    suspend fun getData(): Response<List<AlQuranResponse>>
}
