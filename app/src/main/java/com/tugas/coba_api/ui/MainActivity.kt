package com.tugas.coba_api.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
//import com.google.firebase.database.DataSnapshot
//import com.google.firebase.database.DatabaseError
//import com.google.firebase.database.ValueEventListener
//import com.google.firebase.database.ktx.database
//import com.google.firebase.ktx.Firebase
import com.tugas.coba_api.R
import com.tugas.coba_api.adapter.AdapterSurah
import com.tugas.coba_api.data.al_quran.AlQuranResponse
import com.tugas.coba_api.data.api.ApiAlQuran
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
            // Pindah ke DetailActivity dengan data nomor surah
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("Id_Surah", surah.nomor)
            startActivity(intent)
        }

        binding.recyclerView.adapter = adapter

        // Mengambil data dari API
        lifecycleScope.launch {
            try {
                val response = ApiAlQuran.apiService.getSurah()
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

//val database = Firebase.database
//val myRef = database.getReference("message")
//
//myRef.setValue("Hello, World!")
//
//// Read from the database
//myRef.addValueEventListener(object: ValueEventListener {
//
//    override fun onDataChange(snapshot: DataSnapshot) {
//        // This method is called once with the initial value and again
//        // whenever data at this location is updated.
//        val value = snapshot.getValue()
//        Log.d("test_firebase", "Value is: " + value)
//    }
//
//    override fun onCancelled(error: DatabaseError) {
//        Log.w("test_firebase", "Failed to read value.", error.toException())
//    }
//
//})


