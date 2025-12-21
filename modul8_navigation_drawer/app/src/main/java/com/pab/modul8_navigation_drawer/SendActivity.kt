package com.pab.modul8_navigation_drawer

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.textfield.TextInputEditText

class SendActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send)

        // Setup toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar_send)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Send"

        // Setup button to go to fragment
        val btnGoToFragment: Button = findViewById(R.id.btn_go_to_fragment)
        btnGoToFragment.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("load_fragment", "send")
            startActivity(intent)
        }

        // Setup send button
        val btnSend: Button = findViewById(R.id.btn_send)
        btnSend.setOnClickListener {
            val etTo: TextInputEditText = findViewById(R.id.et_to)
            val etSubject: TextInputEditText = findViewById(R.id.et_subject)
            val etMessage: TextInputEditText = findViewById(R.id.et_message)

            val to = etTo.text.toString()
            val subject = etSubject.text.toString()
            val message = etMessage.text.toString()

            if (to.isNotEmpty() && subject.isNotEmpty() && message.isNotEmpty()) {
                val draft = Draft(to, subject, message)
                DraftRepository.drafts.add(draft)
                Toast.makeText(this, "Message saved to draft", Toast.LENGTH_SHORT).show()
                // Clear fields
                etTo.text?.clear()
                etSubject.text?.clear()
                etMessage.text?.clear()
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
