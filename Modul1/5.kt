fun main() {
    println("------Looping for------")
    cetak_angka_for()
    println("------Looping while------")
    cetak_angka_while()
    println("------Looping do-while------")
    cetak_angka_do_while()
}

fun cetak_angka_for() { //for
    println("------print angkan 1-5 dengan for------")
    for (i in 1..5) {
        println(i)
    }
}

fun cetak_angka_while() { //while for
    var i = 1
    println("------print angka dengan while 1 sampai 5")
    while (i <= 5) {
        println(i)
        i++
    }
}

fun cetak_angka_do_while() { // do while
    var i = 1
    println("------print angka dengan do while 1 sampai 5")
    do {
        println(i)
        i++
    } while (i <= 5)
}