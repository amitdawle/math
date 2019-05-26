val males = linkedMapOf(
        "abe" to mutableListOf("abi", "eve", "cath", "ivy", "jan", "dee", "fay", "bea", "hope", "gay"),
        "bob" to mutableListOf("cath", "hope", "abi", "dee", "eve", "fay", "bea", "jan", "ivy", "gay"),
        "col" to mutableListOf("hope", "eve", "abi", "dee", "bea", "fay", "ivy", "gay", "cath", "jan"),
        "dan" to mutableListOf("ivy", "fay", "dee", "gay", "hope", "eve", "jan", "bea", "cath", "abi"),
        "ed" to mutableListOf("jan", "dee", "bea", "cath", "fay", "eve", "abi", "ivy", "hope", "gay"),
        "fred" to mutableListOf("bea", "abi", "dee", "gay", "eve", "ivy", "cath", "jan", "hope", "fay"),
        "gav" to mutableListOf("gay", "eve", "ivy", "bea", "cath", "abi", "dee", "hope", "jan", "fay"),
        "hal" to mutableListOf("abi", "eve", "hope", "fay", "ivy", "cath", "jan", "bea", "gay", "dee"),
        "ian" to mutableListOf("hope", "cath", "dee", "gay", "bea", "abi", "fay", "ivy", "jan", "eve"),
        "jon" to mutableListOf("abi", "fay", "jan", "gay", "eve", "bea", "dee", "cath", "ivy", "hope"))

val females = linkedMapOf(
        "abi" to listOf("bob", "fred", "jon", "gav", "ian", "abe", "dan", "ed", "col", "hal"),
        "bea" to listOf("bob", "abe", "col", "fred", "gav", "dan", "ian", "ed", "jon", "hal"),
        "cath" to listOf("fred", "bob", "ed", "gav", "hal", "col", "ian", "abe", "dan", "jon"),
        "dee" to listOf("fred", "jon", "col", "abe", "ian", "hal", "gav", "dan", "bob", "ed"),
        "eve" to listOf("jon", "hal", "fred", "dan", "abe", "gav", "col", "ed", "ian", "bob"),
        "fay" to listOf("bob", "abe", "ed", "ian", "jon", "dan", "fred", "gav", "col", "hal"),
        "gay" to listOf("jon", "gav", "hal", "fred", "bob", "abe", "col", "ed", "dan", "ian"),
        "hope" to listOf("gav", "jon", "bob", "abe", "ian", "dan", "hal", "ed", "col", "fred"),
        "ivy" to listOf("ian", "col", "hal", "gav", "fred", "bob", "abe", "ed", "jon", "dan"),
        "jan" to listOf("ed", "hal", "gav", "abe", "bob", "jon", "col", "ian", "fred", "dan"))


fun main(args: Array<String>) {
   stableMarriagePairs().forEach{ (f, m) -> println("$f and $m are now engaged.") }
}


fun stableMarriagePairs(): Map<String, String> {
    val suitors = males.keys.toList()
    val pairs: Map<String, String> = mapOf()
    return findPairs(suitors, pairs)
}

fun findPairs(suitors: List<String>?, engaged: Map<String, String>): Map<String, String> {
    suitors?.let {
        when (suitors.isEmpty()) {
            true -> return engaged
            false -> {
                val suitor = suitors[0]
                val suitees = males[suitor]
                val suitee = suitees?.find { suitee ->
                    println("? $suitor is proposing to $suitee")
                    isPreferred(suitee, suitor, engaged[suitee]) }
                println("+ $suitee has accepted $suitor's proposal")
                val remainingSuitors = if (engaged.contains(suitee)) {
                    val oldSuitor = engaged[suitee]
                    oldSuitor?.let {
                        println("- $suitee and $oldSuitor are no longer engaged")
                        suitors.drop(1).plus(oldSuitor) }
                } else {
                    suitors.drop(1)
                }

                suitee?.let {
                    println("* $suitee and $suitor are now engaged")
                    return findPairs(remainingSuitors, engaged.plus(Pair(suitee, suitor)))
                }
            }
        }
    }
    return engaged
}


fun isPreferred(suitee: String, suitor: String, currentSuitor: String?): Boolean {
    when (currentSuitor) {
        null -> return true
        else -> {
            val preferred = females[suitee]
            preferred?.let { p ->
                return p.indexOf(suitor) < p.indexOf(currentSuitor)
            }
            return false
        }
    }
}
