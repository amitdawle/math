import java.lang.Math.*

class Polynomial(val coefficients: List<Double>) {

    override fun toString(): String {
        return coefficients.zip(0.until(coefficients.size))
                .filter { p -> p.first != 0.0 }
                .map { p -> p.first.toString() + if (p.second == 0) "" else "x^" + p.second.toString() }
                .foldRight("") { x, t -> if (t == "") x else if (t.startsWith("-")) "$x $t" else "$x + $t" }
    }


    fun add(other: Polynomial): Polynomial {
        val n = max(coefficients.size, other.coefficients.size)
        val sums = (coefficients + List(n - coefficients.size) { 0.0 })
                .zip(other.coefficients + List(n - other.coefficients.size) { 0.0 })
                .map { p -> p.first + p.second }
        return Polynomial(sums)
    }

    fun multiply(other: Polynomial): Polynomial {
        return coefficients.iterator().withIndex().asSequence()
                .map { indexedValue ->
                    val index = indexedValue.index
                    val coefficient = indexedValue.value
                    val padded = List(index) { 0.0 } + other.coefficients
                    padded.map { v -> v * coefficient }
                }
                .map { l -> Polynomial(l) }
                .reduce { p1, p2 -> p1.add(p2) }
    }

    fun at(x: Double): Double {
        return this.coefficients.iterator().withIndex().asSequence()
                .fold(0.0) { i, term ->
                    val p = term.index * 1.0
                    val v = term.value
                    i + v * Math.pow(x, p)
                }
    }
}


fun main(args: Array<String>) {

    val x = listOf(0.0, PI/8, PI/4 , 3 * PI/ 8 , PI/2, 5 * PI/ 8,  3 * PI/ 4, 7 * PI/8 , PI,
            PI + PI/8, PI + PI/4 , PI + 3 * PI/ 8 , PI + PI/2, PI + 5 * PI/ 8, PI +  3 * PI/ 4, PI +  7 * PI/8, PI + PI)
    println(x)
    val points = x.map { xi -> Pair(xi , sin(xi)) }
    val p0 = interPolatingPolynomial(points)
    println(p0)

    val p = Polynomial(listOf(1.0, 0.0, -3.0))
    println(p0)
    println(p0.multiply(p0))
    println(p0.multiply(p0).at(2.0))
    println(interPolatingPolynomial(listOf(Pair(0.0,1.0), Pair(2.0, 5.0), Pair(4.0,17.0), Pair(7.0, 19.0))))

    println(interPolatingPolynomial(listOf(Pair(-1.0,0.3678794), Pair(0.0, 1.0), Pair(1.0,2.718282))))
    println(interPolatingPolynomial(listOf(Pair(0.0, 3.0))).at(1.0))

    val points2 = (-10).until(10).map { xi -> Pair(xi.toDouble(), Math.exp(xi.toDouble())) }
    val p2 = interPolatingPolynomial(points2)
    println(p)
    val y = points.map { it.second }
    println(y)
    val calculatedY = points.map { it.first }.map { p.at(it) }
    println(calculatedY)
    print(y.zip(calculatedY).map { pair -> if ((pair.first - pair.second) < 0.00001) 0.0 else pair.first - pair.second })

    println(p.multiply(Polynomial(listOf(-1.0))))
    println(p.add(p))
     println(p.add(p2))
        val p3 = Polynomial(listOf(1.0, 2.0, -3.0))
        println(p3)
        val p4 = Polynomial(listOf(1.0, 2.0, -3.0, 4.0, -5.0))
        println(p4)
        println(p2.add(p4))
}

fun interPolatingPolynomial(points: List<Pair<Double, Double>>): Polynomial {
    return (0.until(points.size))
            .map { i -> term(i, points) }
            .reduce { p1, p2 -> p1.add(p2) }
}


fun term(i: Int, points: List<Pair<Double, Double>>): Polynomial {
    val xi = points[i].first
    val yi = points[i].second

    val otherPoints = points.map { p -> p.first }.filter { x -> x != xi }

    return otherPoints.map { xj -> Polynomial(listOf(-xj / (xi - xj), 1 / (xi - xj))) }
            .foldRight(Polynomial(listOf(yi))) { x, t -> x.multiply(t) }
}