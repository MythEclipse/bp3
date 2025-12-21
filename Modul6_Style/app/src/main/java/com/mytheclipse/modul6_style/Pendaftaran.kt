package com.mytheclipse.modul6_style


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Pendaftaran : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pendaftaran)

        // Optional edge-to-edge padding like in the book
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnSimpan: Button = findViewById(R.id.btnSimpan)
        val btnKembali: Button = findViewById(R.id.btn_kembali)

        btnSimpan.setOnClickListener {
            Toast.makeText(this, "Data pendaftaran disimpan", Toast.LENGTH_SHORT).show()
        }

        btnKembali.setOnClickListener {
            finish()
        }
    }
}