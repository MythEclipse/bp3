package com.mytheclipse.modul3kalkulator

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SegitigaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_segitiga)

        val btn_hitung = findViewById<Button>(R.id.btn_hitung)
        val alas = findViewById<EditText>(R.id.edt_alas)
        val tinggi = findViewById<EditText>(R.id.edt_tinggi)
        val hasil = findViewById<TextView>(R.id.hasil)

        btn_hitung.setOnClickListener {
            val alasValue = alas.text.toString().toDouble()
            val tinggiValue = tinggi.text.toString().toDouble()
            val luas = 0.5 * alasValue * tinggiValue
            hasil.text = luas.toString()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}

