package com.mytheclipse.modul6_style

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val emailField: EditText = findViewById(R.id.etEmail)
        val passwordField: EditText = findViewById(R.id.etPassword)
        val btnMasuk: Button = findViewById(R.id.btnMasuk)
        val btnDaftar: Button = findViewById(R.id.btnDaftar)

        btnMasuk.setOnClickListener {
            val email = emailField.text?.toString().orEmpty().trim()
            val password = passwordField.text?.toString().orEmpty()
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email atau password kosong", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Login berhasil: $email", Toast.LENGTH_SHORT).show()
            }
        }

        btnDaftar.setOnClickListener {
            val intent = Intent(this, Pendaftaran::class.java)
            startActivity(intent)
        }
    }
}