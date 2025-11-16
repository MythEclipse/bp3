package com.mytheclipse.modul4intenteksplisit

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ActivityKedua : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_kedua)

        val pkg = packageName

        //mengambil data dari intent
        val data_univesitas = intent.getStringExtra("Kampus")
        val data_fakultas = intent.getStringExtra("Fakultas")
        val data_prodi = intent.getStringExtra("Prodi")
        val data_matkul = intent.getStringExtra("Matkul")

        //menampilkan data ke layout (lookup IDs at runtime to avoid static analyzer issues)
        val univId = resources.getIdentifier("univ", "id", pkg)
        val fakultasId = resources.getIdentifier("fakultas", "id", pkg)
        val prodiId = resources.getIdentifier("prodi", "id", pkg)
        val matkulId = resources.getIdentifier("matkul", "id", pkg)

        val univ_tampil = findViewById<TextView>(univId)
        val fakultas_tampil = findViewById<TextView>(fakultasId)
        val prodi_tampil = findViewById<TextView>(prodiId)
        val matkul_tampil = findViewById<TextView>(matkulId)

        //menampilkan data ke layout (guard nullable extras)
        univ_tampil?.text = data_univesitas ?: "-"
        fakultas_tampil?.text = data_fakultas ?: "-"
        prodi_tampil?.text = data_prodi ?: "-"
        matkul_tampil?.text = data_matkul ?: "-"


        val mainId = resources.getIdentifier("main", "id", pkg)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(mainId)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}