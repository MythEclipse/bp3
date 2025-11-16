package com.mytheclipse.bp3_tugas_m4

import android.os.Parcel
import android.os.Parcelable

// Data class Food dengan implementasi Parcelable manual (tanpa @Parcelize)
data class Food(
    val nama: String,
    val deskripsi: String,
    val harga: String,
    val kategori: String,
    val bahan: String,
    val asal: String
) : Parcelable {
    // Constructor dari Parcel
    private constructor(parcel: Parcel) : this(
        nama = parcel.readString().orEmpty(),
        deskripsi = parcel.readString().orEmpty(),
        harga = parcel.readString().orEmpty(),
        kategori = parcel.readString().orEmpty(),
        bahan = parcel.readString().orEmpty(),
        asal = parcel.readString().orEmpty()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nama)
        parcel.writeString(deskripsi)
        parcel.writeString(harga)
        parcel.writeString(kategori)
        parcel.writeString(bahan)
        parcel.writeString(asal)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Food> {
        override fun createFromParcel(parcel: Parcel): Food = Food(parcel)
        override fun newArray(size: Int): Array<Food?> = arrayOfNulls(size)
    }
}
