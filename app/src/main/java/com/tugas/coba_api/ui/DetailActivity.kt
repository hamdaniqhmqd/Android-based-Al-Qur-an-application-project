package com.tugas.coba_api.ui

import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.tugas.coba_api.R
import com.tugas.coba_api.adapter.AyatAdapter
import com.tugas.coba_api.data.al_quran.AlQuranResponse
import com.tugas.coba_api.data.al_quran.DetailSurah
import com.tugas.coba_api.data.api.ApiAlQuran
import com.tugas.coba_api.databinding.ActivityDetailBinding
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private var mediaPlayer: MediaPlayer? = null
    private var isPlaying = false // Status untuk audio

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ambil data dari Intent
        val nomorSurah = intent.getIntExtra("Id_Surah", -1)

        // Validasi nomorSurah
        if (nomorSurah != -1) {
            // Panggil API untuk mendapatkan data surah jika nomorSurah valid
            lifecycleScope.launch {
                try {
                    val response = ApiAlQuran.apiService.getSurahByNomor(nomorSurah)
                    if (response.isSuccessful && response.body() != null) {
                        val surah = response.body() // Ambil data surah dari response body
                        Toast.makeText(this@DetailActivity, "Berhasil terhubung", Toast.LENGTH_LONG).show()

                        // Menampilkan data surah pada UI
                        surah?.let {
                            binding.tvNama.text = it.nama
                            binding.tvNamaLatin.text = it.nama_latin
                            binding.tvJumlahAyat.text = "Jumlah Ayat: ${it.jumlah_ayat}"
                            binding.tvTempatTurun.text = "Tempat Turun: ${it.tempat_turun}"
                            binding.tvArti.text = "Arti: ${it.arti}"
                            binding.tvDeskripsi.text = HtmlCompat.fromHtml(it.deskripsi, HtmlCompat.FROM_HTML_MODE_COMPACT)


                            // Menampilkan daftar ayat dalam RecyclerView
                            val adapter = AyatAdapter(it.ayat)
                            binding.recyclerView.layoutManager = LinearLayoutManager(this@DetailActivity)
                            binding.recyclerView.adapter = adapter

                            // Menambahkan logika audio jika ada
                            it.audio?.let { audioUrl ->
                                binding.btnPlayAudio.visibility = View.VISIBLE
                                binding.btnPlayAudio.setOnClickListener {
                                    toggleAudio(audioUrl)
                                }
                            }
                        }
                    } else {
                        Toast.makeText(this@DetailActivity, "Gagal memuat data: ${response.code()}", Toast.LENGTH_LONG).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@DetailActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        } else {
            // Menampilkan pesan kesalahan jika nomorSurah tidak valid
            Toast.makeText(this, "Nomor Surah tidak valid", Toast.LENGTH_SHORT).show()
            // Arahkan ke halaman sebelumnya atau tindakan lain
            finish() // Misalnya, menutup activity ini
        }
    }

    private fun toggleAudio(audioUrl: String) {
        try {
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer().apply {
                    setDataSource(audioUrl)
                    prepare()
                    start()
                }
                isPlaying = true
                binding.btnPlayAudio.text = "Pause"
            } else {
                if (isPlaying) {
                    mediaPlayer?.pause()
                    isPlaying = false
                    binding.btnPlayAudio.text = "Play"
                } else {
                    mediaPlayer?.start()
                    isPlaying = true
                    binding.btnPlayAudio.text = "Pause"
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Gagal memutar audio", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}

