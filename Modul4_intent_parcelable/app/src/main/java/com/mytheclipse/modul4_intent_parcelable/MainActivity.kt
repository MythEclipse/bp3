package com.mytheclipse.modul4_intent_parcelable

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val btn_pindah_dengan_objek: Button = findViewById(R.id.btn_pindah_objek)
        btn_pindah_dengan_objek.setOnClickListener{
            // Membuat objek Mahasiswa
            val Mahasiswa = Mahasiswa(
                "Asep Haryana",
                "20230810043@uniku.ac.id",
                "20230810043",
                20,
                "Kuningan"
            )
            // Memasukkan objek Mahasiswa ke dalam Intent dengan kunci "extra_mahasiswa"
            val pindahDenganObjek = Intent(this@MainActivity, PindahDenganObjek::class.java)
            pindahDenganObjek.putExtra(PindahDenganObjek.EXTRA_MAHASISWA, Mahasiswa)
            startActivity(pindahDenganObjek)

        }

    }
}