fun main() {
    val nilaiSiswa = 85 
    val predikat: String

    predikat = when (nilaiSiswa) {
        in 80..100 -> "A" // Jika nilai ada di rentang 80 sampai 100
        in 60..79  -> "B" // Jika nilai ada di rentang 60 sampai 79
        in 50..59  -> "C" // Jika nilai ada di rentang 50 sampai 59
        else       -> "D" // Jika nilai di bawah 50
    }

    println("Siswa dengan nilai $nilaiSiswa mendapatkan predikat: $predikat")
}