package com.mytheclipse.modul4intenteksplisit

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mytheclipse.modul4intenteksplisit.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val btn_pindah: Button = findViewById(R.id.btn_pindah)
        val btn_pindah_dengan_data: Button = findViewById(R.id.btn_pindah_dengan_data)

        //pindah activity
        btn_pindah.setOnClickListener {
            val pindah_activity: Intent = Intent(this, ActivityKedua::class.java)
            startActivity(pindah_activity)
        }
        //intent dengan data
        btn_pindah_dengan_data.setOnClickListener {
            val pindah_bawa_data: Intent = Intent(this, ActivityKedua::class.java).apply {
                putExtra("Kampus", "Universitas Kuningan")
                putExtra("Fakultas", "Fakultas Ilmu Komputer")
                putExtra("Prodi", "Manajemen Informatika")
                putExtra("Matkul", "Pemrograman Aplikasi Bergerak")
            }
            startActivity(pindah_bawa_data)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}