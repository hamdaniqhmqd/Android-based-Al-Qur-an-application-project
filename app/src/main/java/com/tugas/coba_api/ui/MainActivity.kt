package com.tugas.coba_api.ui

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.recyclerview.widget.LinearLayoutManager
import com.tugas.coba_api.R
import com.tugas.coba_api.adapter.AdapterSurah
import com.tugas.coba_api.data.api.ApiService
import com.tugas.coba_api.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val BASE_URL = "https://quran-api.santrikoding.com/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        // Inisialisasi adapter dengan onClick handling
        val adapter = AdapterSurah { surah ->
            // Pindah ke DetailActivity dengan data item
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("SURAH_DETAIL", surah) // Kirim data menggunakan Parcelable atau Serializable
            startActivity(intent)
        }
        binding.recyclerView.adapter = adapter

        // Inisialisasi Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

        // Mengambil data dari API
        lifecycleScope.launch {
            try {
                val response = retrofit.getData()
                if (response.isSuccessful && response.body() != null) {
                    Toast.makeText(this@MainActivity, "Berhasil terhubung", Toast.LENGTH_LONG).show()
                    adapter.submitList(response.body()) // Masukkan data ke adapter
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "Gagal memuat data: ${response.code()}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}



