# Aplikasi Makanan Indonesia

Aplikasi Android sederhana yang mendemonstrasikan penggunaan **Intent Eksplisit**, **Intent Implisit**, dan **Parcelable** dengan tema makanan Indonesia.

## 🎯 Fitur Utama

### 1. Intent Eksplisit (Explicit Intent)
- Navigasi dari `MainActivity` ke `DetailActivity`
- Passing data objek `Food` menggunakan **Parcelable**
- Click item makanan di RecyclerView untuk melihat detail lengkap

### 2. Intent Implisit (Implicit Intent)
Aplikasi ini mengimplementasikan berbagai implicit intent:

#### Di MainActivity:
- **📤 Share App**: Membagikan informasi aplikasi (ACTION_SEND)
- **🌐 Website**: Membuka website kuliner (ACTION_VIEW)
- **📞 Call**: Menghubungi restoran (ACTION_DIAL)

#### Di DetailActivity:
- **📤 Bagikan Makanan**: Share detail makanan via berbagai app (ACTION_SEND)
- **🌐 Cari Resep**: Membuka Google untuk mencari resep (ACTION_VIEW)
- **📞 Telepon Restoran**: Dial nomor telepon restoran (ACTION_DIAL)

### 3. Parcelable Implementation
- Data class `Food` mengimplementasikan `Parcelable`
- Menggunakan `@Parcelize` annotation dari plugin `kotlin-parcelize`
- Efficient data passing antar Activity

## 📱 Struktur Aplikasi

```
com.mytheclipse.bp3_tugas_m4/
├── Food.kt                 # Data class dengan Parcelable
├── MainActivity.kt         # Activity utama dengan RecyclerView
├── DetailActivity.kt       # Activity detail makanan
├── FoodAdapter.kt          # Adapter untuk RecyclerView
└── res/layout/
    ├── activity_main.xml       # Layout MainActivity
    ├── activity_detail.xml     # Layout DetailActivity
    └── item_food.xml           # Layout item RecyclerView
```

## 🍽️ Data Makanan

Aplikasi menampilkan 8 makanan Indonesia populer:
1. **Nasi Goreng** - Makanan Berat
2. **Rendang** - Makanan Berat (Sumatera Barat)
3. **Sate Ayam** - Makanan Berat (Jawa)
4. **Gado-gado** - Makanan Ringan (Jakarta)
5. **Soto Ayam** - Makanan Berat (Jawa Tengah)
6. **Bakso** - Makanan Berat
7. **Nasi Uduk** - Makanan Berat (Jakarta)
8. **Es Cendol** - Minuman (Jawa Barat)

## 🔧 Teknologi yang Digunakan

- **Kotlin** - Bahasa pemrograman utama
- **ViewBinding** - Untuk akses view yang type-safe
- **RecyclerView** - Menampilkan list makanan
- **Material Design 3** - Komponen UI modern
- **Parcelable** - Efficient serialization untuk Intent
- **Kotlin Parcelize Plugin** - Simplify Parcelable implementation

## 📋 Persyaratan

- Android Studio Hedgehog atau lebih baru
- Minimum SDK: API 30 (Android 11)
- Target SDK: API 36
- Kotlin 2.0.21
- Gradle 8.13.1

## 🚀 Cara Menjalankan

1. Clone atau download project
2. Buka project di Android Studio
3. Tunggu Gradle sync selesai
4. Build dan run di emulator/device Android

```bash
# Atau jalankan via command line
./gradlew assembleDebug
./gradlew installDebug
```

## 📖 Penjelasan Konsep

### Intent Eksplisit
Intent eksplisit digunakan untuk navigasi antar component dalam aplikasi yang sama. Contoh implementasi:

```kotlin
// Di MainActivity - mengirim data Food ke DetailActivity
val intent = Intent(this, DetailActivity::class.java).apply {
    putExtra("FOOD_DATA", food) // food adalah Parcelable
}
startActivity(intent)
```

### Intent Implisit
Intent implisit digunakan untuk meminta Android system menangani action tertentu. Contoh:

```kotlin
// Share text
val shareIntent = Intent().apply {
    action = Intent.ACTION_SEND
    putExtra(Intent.EXTRA_TEXT, shareText)
    type = "text/plain"
}
startActivity(Intent.createChooser(shareIntent, "Bagikan via"))

// Open URL
val websiteIntent = Intent(Intent.ACTION_VIEW).apply {
    data = Uri.parse("https://www.google.com")
}
startActivity(websiteIntent)

// Dial phone
val phoneIntent = Intent(Intent.ACTION_DIAL).apply {
    data = Uri.parse("tel:081234567890")
}
startActivity(phoneIntent)
```

### Parcelable
Parcelable adalah interface Android untuk serialisasi objek yang lebih efisien dibanding Serializable:

```kotlin
@Parcelize
data class Food(
    val nama: String,
    val deskripsi: String,
    val harga: String,
    val kategori: String,
    val bahan: String,
    val asal: String
) : Parcelable
```

## 📸 Screenshot

_Aplikasi menampilkan UI yang clean dan modern dengan Material Design 3 components._

## 👨‍💻 Developer

Dibuat untuk tugas Modul 4 BP3 - Demonstrasi Intent dan Parcelable

## 📄 Lisensi

Project ini dibuat untuk keperluan edukasi.

