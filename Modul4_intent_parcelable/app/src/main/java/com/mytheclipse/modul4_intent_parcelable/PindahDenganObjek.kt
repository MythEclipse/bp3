package com.mytheclipse.modul4_intent_parcelable

import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class PindahDenganObjek : AppCompatActivity() {

    companion object { const val EXTRA_MAHASISWA = "extra_mahasiswa" }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pindah_dengan_objek)

        val tv_obj_nama: TextView = findViewById(R.id.tv_object_nama)
        val tv_obj_nim: TextView = findViewById(R.id.tv_object_nim)
        val tv_obj_email: TextView = findViewById(R.id.tv_object_email)
        val tv_obj_kota: TextView = findViewById(R.id.tv_object_kota)
        val tv_obj_umur: TextView = findViewById(R.id.tv_object_usia)

        val mahasiswa = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra<Mahasiswa>(EXTRA_MAHASISWA, Mahasiswa::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<Mahasiswa>(EXTRA_MAHASISWA)
        }

        if (mahasiswa != null) {
            val nama = "Nama: ${mahasiswa.name.toString()}"
            val email = "Email: ${mahasiswa.email.toString()}"
            val nim = "NIM: ${mahasiswa.nim.toString()}"
            val umur = "Umur: ${mahasiswa.umur.toString()}"
            val kota = "Kota: ${mahasiswa.kota.toString()}"

            tv_obj_nama.text = nama
            tv_obj_nim.text = nim
            tv_obj_email.text = email
            tv_obj_kota.text = kota
            tv_obj_umur.text = umur

        }

    }


}