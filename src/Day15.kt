import Position.Companion.manhattanDistance
import kotlin.math.abs

fun main() {
    fun part1(input: List<Position>, row: Int): Int {
        val sensorsAffectingRow = input.filter {
            abs(it.sensorY - row) <= it.manhattanDistance()
        }
        val possiblePositions = sensorsAffectingRow.map {
            val (xMin, xMax) = funMath(it.sensorX, it.sensorY, row, it.manhattanDistance())
            IntRange(xMin, xMax).toSet()
        }.reduce { a, b -> a.union(b) }

        val beaconsInRow = sensorsAffectingRow.filter {
            it.beaconY == row
        }.map {
            it.beaconX
        }.toSet()

        return (possiblePositions subtract beaconsInRow).size
    }

    fun part2(input: List<Position>): Int {

//        (0..4000000).first {
//            part1(input, it)
//        }
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    //val input = readInput("Day15_test")
    //check(part1(testInput) == 1)

    val input = readInput("Day15")
    val regex = Regex("x=(-?\\d+), y=(-?\\d+)")
    val coordinates = input.map {
        val matchResults = regex.findAll(it).toList()

        Position(
            matchResults[0].groups[1]!!.value.toInt(),
            matchResults[0].groups[2]!!.value.toInt(),
            matchResults[1].groups[1]!!.value.toInt(),
            matchResults[1].groups[2]!!.value.toInt()
        )
    }
    println(part1(coordinates, 2000000))
}

data class Position(
    val sensorX: Int,
    val sensorY: Int,
    val beaconX: Int,
    val beaconY: Int
) {
    companion object {
        fun Position.manhattanDistance() = getManhattanDistance(sensorX, sensorY, beaconX, beaconY)
    }
}

private fun getManhattanDistance(x1:Int, y1: Int, x2: Int, y2: Int) =
    abs(x1 - x2) + abs(y1 - y2)

private fun funMath(sensorX: Int, sensorY: Int, givenY: Int, maxDistance: Int): Pair<Int, Int>{
    val yDifference = abs(sensorY - givenY)
    val rhs = maxDistance - yDifference
    val xGreaterEqualThan = (rhs - sensorX) * -1
    val xLessEqualThan = (-rhs - sensorX) * -1
    return xGreaterEqualThan to xLessEqualThan
}

