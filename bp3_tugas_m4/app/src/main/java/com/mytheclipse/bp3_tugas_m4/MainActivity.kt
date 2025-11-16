package com.mytheclipse.bp3_tugas_m4

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var foodAdapter: FoodAdapter
    private lateinit var rvFood: RecyclerView
    private lateinit var btnShareApp: Button
    private lateinit var btnWebsite: Button
    private lateinit var btnCallRestaurant: Button

    private val foodList = listOf(
        Food(
            nama = "Nasi Goreng",
            deskripsi = "Nasi goreng adalah makanan berupa nasi yang digoreng dengan bumbu-bumbu yang kaya rasa. Biasanya dicampur dengan kecap manis, bawang merah, bawang putih, dan cabai.",
            harga = "Rp 15.000 - Rp 25.000",
            kategori = "Makanan Berat",
            bahan = "Nasi, kecap manis, telur, ayam/udang, bawang merah, bawang putih, cabai",
            asal = "Indonesia"
        ),
        Food(
            nama = "Rendang",
            deskripsi = "Rendang adalah masakan daging yang menggunakan campuran berbagai bumbu dan rempah-rempah. Dimasak dalam waktu lama dengan santan kelapa hingga kuah mengental.",
            harga = "Rp 30.000 - Rp 50.000",
            kategori = "Makanan Berat",
            bahan = "Daging sapi, santan kelapa, cabai, bawang merah, bawang putih, lengkuas, jahe, kunyit",
            asal = "Sumatera Barat"
        ),
        Food(
            nama = "Sate Ayam",
            deskripsi = "Sate ayam adalah makanan yang terbuat dari potongan daging ayam yang ditusuk dengan tusuk sate kemudian dipanggang dengan bumbu kacang yang gurih.",
            harga = "Rp 20.000 - Rp 35.000",
            kategori = "Makanan Berat",
            bahan = "Daging ayam, bumbu kacang, kecap manis, bawang merah, jeruk nipis",
            asal = "Jawa"
        ),
        Food(
            nama = "Gado-gado",
            deskripsi = "Gado-gado adalah salad khas Indonesia yang berisi sayur-sayuran yang direbus dan dicampur dengan bumbu kacang, lontong, dan telur rebus.",
            harga = "Rp 15.000 - Rp 25.000",
            kategori = "Makanan Ringan",
            bahan = "Sayuran (kangkung, kol, tauge), lontong, telur, tahu, tempe, bumbu kacang",
            asal = "Jakarta"
        ),
        Food(
            nama = "Soto Ayam",
            deskripsi = "Soto ayam adalah makanan berkuah khas Indonesia dengan isian suwiran ayam, sayuran, dan telur. Disajikan dengan nasi atau lontong.",
            harga = "Rp 18.000 - Rp 30.000",
            kategori = "Makanan Berat",
            bahan = "Ayam, kunyit, bawang putih, serai, daun jeruk, soun, telur rebus, tauge",
            asal = "Jawa Tengah"
        ),
        Food(
            nama = "Bakso",
            deskripsi = "Bakso adalah bola daging yang biasanya dibuat dari campuran daging sapi giling dan tepung. Disajikan dalam kuah kaldu panas dengan mie dan sayuran.",
            harga = "Rp 15.000 - Rp 30.000",
            kategori = "Makanan Berat",
            bahan = "Daging sapi giling, tepung tapioka, bawang putih, mie, kaldu sapi",
            asal = "Indonesia"
        ),
        Food(
            nama = "Nasi Uduk",
            deskripsi = "Nasi uduk adalah nasi yang dimasak dengan santan dan rempah-rempah, menghasilkan aroma yang wangi dan rasa yang gurih. Biasanya disajikan dengan lauk pauk.",
            harga = "Rp 12.000 - Rp 20.000",
            kategori = "Makanan Berat",
            bahan = "Beras, santan kelapa, serai, daun salam, daun pandan",
            asal = "Jakarta"
        ),
        Food(
            nama = "Es Cendol",
            deskripsi = "Es cendol adalah minuman segar khas Indonesia yang terbuat dari cendol (tepung beras), santan, gula merah cair, dan es serut.",
            harga = "Rp 8.000 - Rp 15.000",
            kategori = "Minuman",
            bahan = "Cendol, santan, gula merah, es batu, daun pandan",
            asal = "Jawa Barat"
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        setupRecyclerView()
        setupImplicitIntents()
    }

    private fun initViews() {
        rvFood = findViewById(R.id.rvFood)
        btnShareApp = findViewById(R.id.btnShareApp)
        btnWebsite = findViewById(R.id.btnWebsite)
        btnCallRestaurant = findViewById(R.id.btnCallRestaurant)
    }

    private fun setupRecyclerView() {
        foodAdapter = FoodAdapter(foodList) { food ->
            val intent = Intent(this, DetailActivity::class.java).apply {
                putExtra("FOOD_DATA", food)
            }
            startActivity(intent)
        }
        rvFood.layoutManager = LinearLayoutManager(this)
        rvFood.adapter = foodAdapter
    }

    private fun setupImplicitIntents() {
        btnShareApp.setOnClickListener {
            val shareText = """
                🍽️ Aplikasi Makanan Indonesia
                
                Temukan berbagai resep dan informasi makanan tradisional Indonesia!
                
                Download sekarang dan jelajahi kekayaan kuliner nusantara.
            """.trimIndent()
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                putExtra(Intent.EXTRA_TEXT, shareText)
                type = "text/plain"
            }
            startActivity(Intent.createChooser(shareIntent, "Bagikan aplikasi via"))
        }

        btnWebsite.setOnClickListener {
            val websiteIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.kuliner-indonesia.com"))
            startActivity(websiteIntent)
        }

        btnCallRestaurant.setOnClickListener {
            val phoneIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:081234567890"))
            startActivity(phoneIntent)
        }
    }
}
