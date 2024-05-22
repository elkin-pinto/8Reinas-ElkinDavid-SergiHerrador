import java.util.*

fun main() {
    val scan = Scanner(System.`in`)
    var tamanyTauler : Int? = null

    do {
        println("Digues el tamany del tauler:")

        if (scan.hasNextInt()) {
            tamanyTauler = scan.nextInt()
            if (tamanyTauler <= 0) {
                println("Número negativo")
            }
        } else {
            scan.next()
            println("Dato no númerico")
        }
    } while (tamanyTauler == null)

    val reines = n_reines()

    var opcioClient: Int? = null

    do {
        println("Digues si vols totes les soluciones o només 1 (Recomanat per tamanys molt grans)")
        println(
            "1. Totes les solucions\n" +
                    "2. Una solució"
        )
        if (scan.hasNextInt()) {
            opcioClient = scan.nextInt()
            if (opcioClient <= 0) {
                println("Número negativo")
            }
        } else {
            scan.next()
            println("Dato no númerico")
        }
    } while (opcioClient == null)

    when (opcioClient) {
        1 -> reines.cercaTotes_solucions(tamanyTauler)
        2 -> reines.cerca_solucions(tamanyTauler, true)
    }

    val numeroSolucions = reines.num_solucions()
    println(numeroSolucions)


    var vistaSolucio: Int? = null

    do {
        println("Pon la solucion que quieras ver :: ")
        if (scan.hasNextInt()) {
            vistaSolucio = scan.nextInt()
            if (vistaSolucio !in 1..numeroSolucions) {
                println("Número fuera de rango")
                vistaSolucio = null
            }
        } else {
            scan.next()
            println("Dato no númerico")
        }
    } while (vistaSolucio == null)

    println(reines.visualitza_solucio(vistaSolucio))
}


class n_reines {
    private lateinit var solucions: MutableList<String>
    private var nSolucions = 0
    private var fila = 0
    private lateinit var columna: IntArray
    private var seguir = false
    private var tamanyTauler = 0

    fun cerca_solucions(tamanyTauler: Int, seguir: Boolean) {
        this.nSolucions = 0
        solucions = mutableListOf()
        columna = IntArray(tamanyTauler + 1)
        fila = 1
        columna[fila] = 0
        this.seguir = seguir
        this.tamanyTauler = tamanyTauler

        while (fila > 0 && this.seguir) {
            if (columna[fila] < tamanyTauler) {
                columna[fila] += 1
                if (nodoValid(fila, columna)) {
                    if (fila == tamanyTauler) {
                        tractarSolucio(columna)
                    } else {
                        fila += 1
                        columna[fila] = 0
                    }
                }
            } else {
                fila -= 1
            }
        }
    }

    fun cercaTotes_solucions(tamanyTauler: Int) {
        this.nSolucions = 0
        solucions = mutableListOf()
        columna = IntArray(tamanyTauler + 1)
        fila = 1
        columna[fila] = 0
        this.tamanyTauler = tamanyTauler

        while (fila > 0) {
            if (columna[fila] < tamanyTauler) {
                columna[fila] += 1
                if (nodoValid(fila, columna)) {
                    if (fila == tamanyTauler) {
                        tractarSolucio(columna)
                    } else {
                        fila += 1
                        columna[fila] = 0
                    }
                }
            } else {
                fila -= 1
            }
        }
    }

    fun num_solucions(): Int {
        return this.nSolucions
    }

    fun visualitza_solucio(ns: Int): String? {
        return if (this.solucions.isNotEmpty()) this.solucions[ns - 1] else null
    }


    private fun nodoValid(fila: Int, columna: IntArray): Boolean {
        for (f in 1 until fila) {

            if (columna[f] == columna[fila]) return false


            if (f - columna[f] == fila - columna[fila]) return false


            if (f + columna[f] == fila + columna[fila]) return false
        }
        return true
    }


    private fun tractarSolucio(columna: IntArray) {
        var solucio = ""

        for (f in 1..tamanyTauler) {
            solucio += "Reina $f :: fila::$f columna::${columna[f]}\n"
        }
        this.solucions.add(solucio)
        nSolucions++

        if (this.seguir) {
            this.seguir = false
        }

    }
}