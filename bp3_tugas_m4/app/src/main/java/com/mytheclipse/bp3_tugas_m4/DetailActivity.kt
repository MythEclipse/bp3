package com.mytheclipse.bp3_tugas_m4

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {
    private lateinit var food: Food

    // View references (tanpa ViewBinding)
    private lateinit var tvDetailNama: TextView
    private lateinit var tvDetailKategori: TextView
    private lateinit var tvDetailHarga: TextView
    private lateinit var tvDetailDeskripsi: TextView
    private lateinit var tvDetailBahan: TextView
    private lateinit var tvDetailAsal: TextView
    private lateinit var btnShare: Button
    private lateinit var btnWebsite: Button
    private lateinit var btnCall: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        initViews()
        receiveParcelable()
        displayFoodDetails()
        setupImplicitIntents()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    private fun initViews() {
        tvDetailNama = findViewById(R.id.tvDetailNama)
        tvDetailKategori = findViewById(R.id.tvDetailKategori)
        tvDetailHarga = findViewById(R.id.tvDetailHarga)
        tvDetailDeskripsi = findViewById(R.id.tvDetailDeskripsi)
        tvDetailBahan = findViewById(R.id.tvDetailBahan)
        tvDetailAsal = findViewById(R.id.tvDetailAsal)
        btnShare = findViewById(R.id.btnShare)
        btnWebsite = findViewById(R.id.btnWebsite)
        btnCall = findViewById(R.id.btnCall)
    }

    private fun receiveParcelable() {
        food = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("FOOD_DATA", Food::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra("FOOD_DATA")
        } ?: run {
            Toast.makeText(this, "Data makanan tidak ditemukan", Toast.LENGTH_SHORT).show()
            finish()
            return
        }
    }

    private fun displayFoodDetails() {
        tvDetailNama.text = food.nama
        tvDetailKategori.text = "Kategori: ${food.kategori}"
        tvDetailHarga.text = food.harga
        tvDetailDeskripsi.text = food.deskripsi
        tvDetailBahan.text = "Bahan: ${food.bahan}"
        tvDetailAsal.text = "Asal: ${food.asal}"
    }

    private fun setupImplicitIntents() {
        // Share makanan
        btnShare.setOnClickListener {
            val shareText = """
                🍽️ ${food.nama}\n\n${food.deskripsi}\n\nHarga: ${food.harga}\nKategori: ${food.kategori}\nAsal: ${food.asal}\n\n#MakananIndonesia #${food.nama.replace(" ", "")}
            """.trimIndent()
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                putExtra(Intent.EXTRA_TEXT, shareText)
                type = "text/plain"
            }
            startActivity(Intent.createChooser(shareIntent, "Bagikan makanan via"))
        }

        // Buka website resep
        btnWebsite.setOnClickListener {
            val searchQuery = food.nama.replace(" ", "+")
            val websiteIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q=resep+$searchQuery"))
            startActivity(websiteIntent)
        }

        // Telepon restoran
        btnCall.setOnClickListener {
            val phoneIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:081234567890"))
            startActivity(phoneIntent)
        }
    }
}
