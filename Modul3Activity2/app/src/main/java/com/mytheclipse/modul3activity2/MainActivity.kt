package com.mytheclipse.modul3activity2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // inisiasikan button ke variabel (menggunakan id yang ada di layout: btn_pindah_halaman)
        val btnPindah = findViewById<Button>(R.id.btn_pindah_halaman)
        // aksi ketika button diklik: pindah ke HalamanSelanjutnya
        btnPindah.setOnClickListener {
            val intent = Intent(this, HalamanSelanjutnya::class.java)
            startActivity(intent)
        }
    }
}