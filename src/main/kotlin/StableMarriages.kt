val femalesPreferredByMales = linkedMapOf(
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

val malesPreferredByFemales = linkedMapOf(
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
    val suitors = femalesPreferredByMales.keys.toList()
    val pairs: Map<String, String> = mapOf()
    return findPairs(suitors, pairs)
}

fun findPairs(males: List<String>?, engaged: Map<String, String>): Map<String, String> {
    males?.let {
        when (males.isEmpty()) {
            true -> return engaged
            false -> {
                val male = males[0]
                val females = femalesPreferredByMales[male]
                val female = females?.find { female ->
                    println("? $male is proposing to $female")
                    isPreferred(female, male, engaged[female]) }
                println("+ $female has accepted $male's proposal")
                
                val remainingSuitors = if (engaged.contains(female)) {
                    val oldSuitor = engaged[female]
                    oldSuitor?.let {
                        println("- $female and $oldSuitor are no longer engaged")
                        males.drop(1).plus(oldSuitor) }
                } else {
                    males.drop(1)
                }

                female?.let {
                    println("* $female and $male are now engaged")
                    return findPairs(remainingSuitors, engaged.plus(Pair(female, male)))
                }
            }
        }
    }
    return engaged
}


fun isPreferred(female: String, suitor: String, engagedTo: String?): Boolean {
    when (engagedTo) {
        null -> return true
        else -> {
            val preferredMales = malesPreferredByFemales[female]
            preferredMales?.let { p ->
                return p.indexOf(suitor) < p.indexOf(engagedTo)
            }
            return false
        }
    }
}
