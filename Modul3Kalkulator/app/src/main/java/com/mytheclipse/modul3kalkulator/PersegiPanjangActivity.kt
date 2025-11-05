package com.mytheclipse.modul3kalkulator

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PersegiPanjangActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_persegi_panjang)

        val btn_hitung = findViewById<Button>(R.id.btn_hitung)
        val panjang = findViewById<EditText>(R.id.edt_panjang)
        val lebar = findViewById<EditText>(R.id.edt_lebar)
        val hasil = findViewById<TextView>(R.id.hasil)

        btn_hitung.setOnClickListener {
            val panjangValue = panjang.text.toString().toDouble()
            val lebarValue = lebar.text.toString().toDouble()
            val luas = panjangValue * lebarValue
            hasil.text = luas.toString()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}

