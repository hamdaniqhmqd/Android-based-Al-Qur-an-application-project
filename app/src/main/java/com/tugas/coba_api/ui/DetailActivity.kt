package com.tugas.coba_api.ui

import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.tugas.coba_api.R
import com.tugas.coba_api.data.al_quran.AlQuranResponse
import com.tugas.coba_api.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private var mediaPlayer: MediaPlayer? = null
    private var isPlaying = false // Status untuk audio

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ambil data dari Intent
        val surahDetail: AlQuranResponse? = intent.getParcelableExtra("SURAH_DETAIL")

        // Gunakan data untuk menampilkan detail
        surahDetail?.let { surah ->
            binding.tvNama.text = surah.nama
            binding.tvNamaLatin.text = surah.nama_latin
            binding.tvJumlahAyat.text = "Jumlah Ayat: ${surah.jumlah_ayat}"
            binding.tvTempatTurun.text = "Tempat Turun: ${surah.tempat_turun}"
            binding.tvArti.text = "Arti: ${surah.arti}"
            binding.tvDeskripsi.text = surah.deskripsi

            // Jika ada URL audio, tambahkan tombol untuk play/pause audio
            surah.audio?.let { audioUrl ->
                binding.btnPlayAudio.visibility = View.VISIBLE
                binding.btnPlayAudio.setOnClickListener {
                    toggleAudio(audioUrl) // Panggil fungsi toggleAudio
                }
            }
        }
    }

    private fun toggleAudio(audioUrl: String) {
        try {
            if (mediaPlayer == null) {
                // Inisialisasi MediaPlayer jika null
                mediaPlayer = MediaPlayer().apply {
                    setDataSource(audioUrl)
                    prepare()
                    start()
                }
                isPlaying = true
                binding.btnPlayAudio.text = "Pause" // Ubah teks tombol
            } else {
                if (isPlaying) {
                    // Pause audio
                    mediaPlayer?.pause()
                    isPlaying = false
                    binding.btnPlayAudio.text = "Play" // Ubah teks tombol
                } else {
                    // Resume audio
                    mediaPlayer?.start()
                    isPlaying = true
                    binding.btnPlayAudio.text = "Pause" // Ubah teks tombol
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Gagal memutar audio", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release() // Bersihkan sumber daya
        mediaPlayer = null
    }
}

