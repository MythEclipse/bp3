package com.mytheclipse.modul3kalkulator

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.PI

class LingkaranActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lingkaran)

        val btn_hitung = findViewById<Button>(R.id.btn_hitung)
        val jariJari = findViewById<EditText>(R.id.edt_jari_jari)
        val hasil = findViewById<TextView>(R.id.hasil)

        btn_hitung.setOnClickListener {
            val jariJariValue = jariJari.text.toString().toDouble()
            val luas = PI * jariJariValue * jariJariValue
            hasil.text = String.format("%.2f", luas)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}

