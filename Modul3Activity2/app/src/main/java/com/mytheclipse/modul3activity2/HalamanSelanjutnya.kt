package com.mytheclipse.modul3activity2

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class HalamanSelanjutnya : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_halaman_selanjutnya)

        // tombol untuk kembali ke halaman pertama
        val btnKembali = findViewById<Button>(R.id.btn_pindah_halaman1)
        btnKembali.setOnClickListener {
            finish()
        }
    }
}

