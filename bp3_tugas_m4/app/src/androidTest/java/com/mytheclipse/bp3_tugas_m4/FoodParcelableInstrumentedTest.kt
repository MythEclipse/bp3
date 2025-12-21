package com.mytheclipse.bp3_tugas_m4

import android.os.Parcel
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FoodParcelableInstrumentedTest {
    @Test
    fun food_parcelable_roundTrip() {
        val original = Food(
            nama = "Rendang",
            deskripsi = "Daging dimasak dengan rempah dan santan hingga kering",
            harga = "Rp 40.000",
            kategori = "Makanan Berat",
            bahan = "Daging sapi, santan, cabai, bawang, jahe, lengkuas",
            asal = "Sumatera Barat"
        )
        val parcel: Parcel = Parcel.obtain()
        original.writeToParcel(parcel, 0)
        parcel.setDataPosition(0)
        val recreated = Food.CREATOR.createFromParcel(parcel)
        parcel.recycle()

        assertEquals(original.nama, recreated.nama)
        assertEquals(original.deskripsi, recreated.deskripsi)
        assertEquals(original.harga, recreated.harga)
        assertEquals(original.kategori, recreated.kategori)
        assertEquals(original.bahan, recreated.bahan)
        assertEquals(original.asal, recreated.asal)
    }
}

