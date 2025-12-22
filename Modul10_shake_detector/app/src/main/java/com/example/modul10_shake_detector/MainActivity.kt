package com.example.modul10_shake_detector

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
import kotlin.math.sqrt

class MainActivity : AppCompatActivity(), SensorEventListener {

    // Deklarasi variabel untuk sensor dan komponen
    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var vibrator: Vibrator
    private lateinit var warningTextView: TextView
    private lateinit var statusTextView: TextView
    private lateinit var accelerometerXTextView: TextView
    private lateinit var accelerometerYTextView: TextView
    private lateinit var accelerometerZTextView: TextView
    private lateinit var mainLayout: LinearLayout

    // Variabel untuk deteksi shake
    private var lastUpdate: Long = 0
    private var lastX = 0f
    private var lastY = 0f
    private var lastZ = 0f
    private val shakeThreshold = 800 // Threshold untuk deteksi shake
    
    // Status shake
    private var isShaking = false
    private var shakeEndTime: Long = 0
    private val shakeCooldown = 2000L // Cooldown 2 detik setelah shake

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

        // Mendapatkan sensor accelerometer
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        // Inisialisasi MediaPlayer dengan file alarm
        mediaPlayer = MediaPlayer.create(this, R.raw.alarm)
        mediaPlayer.isLooping = false // Alarm hanya sekali per shake

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
        statusTextView = findViewById(R.id.StatusTextView)
        accelerometerXTextView = findViewById(R.id.AccelerometerXTextView)
        accelerometerYTextView = findViewById(R.id.AccelerometerYTextView)
        accelerometerZTextView = findViewById(R.id.AccelerometerZTextView)
        warningTextView.visibility = View.GONE
    }

    // Menangani perubahan pada sensor accelerometer
    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            val currentTime = System.currentTimeMillis()
            
            // Update nilai accelerometer di UI
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]
            
            accelerometerXTextView.text = "X: %.2f m/s²".format(x)
            accelerometerYTextView.text = "Y: %.2f m/s²".format(y)
            accelerometerZTextView.text = "Z: %.2f m/s²".format(z)

            // Deteksi shake setiap 100ms
            if ((currentTime - lastUpdate) > 100) {
                val diffTime = currentTime - lastUpdate
                lastUpdate = currentTime

                val speed = sqrt(
                    ((x - lastX) * (x - lastX) + 
                     (y - lastY) * (y - lastY) + 
                     (z - lastZ) * (z - lastZ)).toDouble()
                ) / diffTime * 10000

                if (speed > shakeThreshold) {
                    onShakeDetected()
                } else {
                    // Cek apakah cooldown sudah selesai
                    if (isShaking && currentTime > shakeEndTime) {
                        onShakeEnded()
                    }
                }

                lastX = x
                lastY = y
                lastZ = z
            }
        }
    }

    // Dipanggil ketika shake terdeteksi
    private fun onShakeDetected() {
        if (!isShaking) {
            isShaking = true
            
            // Tampilkan peringatan
            warningTextView.visibility = View.VISIBLE
            warningTextView.text = "📱 SHAKE TERDETEKSI! 📱"
            statusTextView.text = "Status: GUNCANGAN TERDETEKSI!"
            statusTextView.setTextColor(resources.getColor(android.R.color.holo_red_light, theme))
            mainLayout.setBackgroundColor(resources.getColor(android.R.color.holo_red_dark, theme))

            // Mainkan alarm jika belum dimainkan
            if (!mediaPlayer.isPlaying) {
                mediaPlayer.seekTo(0)
                mediaPlayer.start()
            }

            // Aktifkan getaran pola shock
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createWaveform(longArrayOf(0, 300, 100, 300, 100, 500), -1))
            } else {
                @Suppress("DEPRECATION")
                vibrator.vibrate(longArrayOf(0, 300, 100, 300, 100, 500), -1)
            }
        }
        
        // Reset cooldown timer setiap ada shake
        shakeEndTime = System.currentTimeMillis() + shakeCooldown
    }

    // Dipanggil ketika shake sudah berhenti (setelah cooldown)
    private fun onShakeEnded() {
        isShaking = false
        
        // Sembunyikan peringatan
        warningTextView.visibility = View.GONE
        statusTextView.text = "Status: Menunggu guncangan..."
        statusTextView.setTextColor(resources.getColor(android.R.color.holo_green_light, theme))
        mainLayout.setBackgroundColor(resources.getColor(android.R.color.background_dark, theme))

        // Hentikan alarm
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
            mediaPlayer.seekTo(0)
        }

        // Hentikan getaran
        vibrator.cancel()
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Tidak perlu implementasi khusus
    }

    override fun onResume() {
        super.onResume()
        // Register sensor listener
        accelerometer?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_GAME)
        }
        lastUpdate = System.currentTimeMillis()
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
