package com.example.modul10_sensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity(), SensorEventListener {

    // Deklarasi variabel untuk sensor dan komponen
    private lateinit var sensorManager: SensorManager
    private var proximitySensor: Sensor? = null
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var vibrator: Vibrator
    private lateinit var warningTextView: TextView
    private lateinit var mainLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inisialisasi komponen
        setupComponents()
    }

    // Inisialisasi komponen
    private fun setupComponents() {
        // Inisialisasi SensorManager
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        // Mendapatkan sensor proximity
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)

        // Inisialisasi MediaPlayer dengan file alarm
        mediaPlayer = MediaPlayer.create(this, R.raw.alarm)
        mediaPlayer.isLooping = true

        // Inisialisasi Vibrator
        vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager = getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }

        // Binding UI components
        mainLayout = findViewById(R.id.main)
        warningTextView = findViewById(R.id.WarningTextView)
        warningTextView.visibility = View.GONE
    }

    // Menangani perubahan pada sensor proximity
    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_PROXIMITY) {
            val distance = event.values[0]
            val maxRange = event.sensor.maximumRange

            if (distance < maxRange) {
                // Objek terdeteksi dekat
                warningTextView.visibility = View.VISIBLE
                warningTextView.text = "⚠️ PERINGATAN! Objek Terdeteksi Dekat ⚠️"

                // Mainkan alarm jika belum dimainkan
                if (!mediaPlayer.isPlaying) {
                    mediaPlayer.start()
                }

                // Aktifkan getaran
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createWaveform(longArrayOf(0, 500, 200, 500), 0))
                } else {
                    @Suppress("DEPRECATION")
                    vibrator.vibrate(longArrayOf(0, 500, 200, 500), 0)
                }
            } else {
                // Objek terdeteksi jauh
                warningTextView.visibility = View.GONE

                // Hentikan alarm
                if (mediaPlayer.isPlaying) {
                    mediaPlayer.pause()
                    mediaPlayer.seekTo(0)
                }

                // Hentikan getaran
                vibrator.cancel()
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Tidak perlu implementasi khusus
    }

    override fun onResume() {
        super.onResume()
        // Register sensor listener
        proximitySensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        // Unregister sensor listener
        sensorManager.unregisterListener(this)

        // Hentikan alarm dan getaran
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
            mediaPlayer.seekTo(0)
        }
        vibrator.cancel()
    }

    override fun onDestroy() {
        super.onDestroy()
        // Release MediaPlayer
        mediaPlayer.release()
    }
}