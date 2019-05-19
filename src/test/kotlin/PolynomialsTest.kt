import junit.framework.Assert.assertTrue
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.junit.Assert
import java.lang.Math.*

class PolynomialsTest : Spek({
    describe("Polynomial Interpolation") {
        given("Given one point") {
            it("returns polynomial with degree 0") {
                val p = interPolatingPolynomial(listOf(Pair(3.0, 2.0))) // f(x) = 2
                Assert.assertEquals(listOf(2.0), p.coefficients)
            }
        }
        given("Given two points") {
            it("returns polynomial (line) with degree 1") {
                val p = interPolatingPolynomial(listOf(Pair(0.0, 0.0), Pair(5.0, 5.0))) // f(x) = x = 0 + 0 x^1
                Assert.assertEquals(listOf(0.0, 1.0), p.coefficients)
            }
        }
        given("Given points on sine curve the interpolated polynomial for different x produces approximately " +
                "same result as sin(x) within a tolerance of 0.001") {
            it("returns polynomial (curve) with degree 2") {

                val x = listOf(0.0, PI / 8, PI / 4, 3 * PI / 8, PI / 2, 5 * PI / 8, 3 * PI / 4, 7 * PI / 8, PI,
                        PI + PI / 8, PI + PI / 4, PI + 3 * PI / 8, PI + PI / 2, PI + 5 * PI / 8, PI + 3 * PI / 4, PI + 7 * PI / 8, PI + PI)

                val points = x.map { xi -> Pair(xi, sin(xi)) }
                val y =  points.map { p -> p.second }
                val p0 = interPolatingPolynomial(points)
                val calculatedY = x.map { x -> p0.at(x) }

               assertTrue( y.zip(calculatedY).map { p -> abs(p.first - p.second)}
                        .all { d -> d < 0.001 }   )

            }
        }
    }

})