import Position.Companion.manhattanDistance
import java.lang.Integer.max
import java.lang.Integer.min
import kotlin.math.abs

fun main() {
    fun positionsWithoutBeaconForRow(input: List<Position>, row: Int): List<IntRange> {
        val sensorsAffectingRow = input.filter {
            abs(it.sensorY - row) <= it.manhattanDistance()
        }
        val xRanges = sensorsAffectingRow.map {
            val (xMin, xMax) = funMath(it.sensorX, it.sensorY, row, it.manhattanDistance())
            Pair(xMin, xMax)
        }.sortedBy { it.first }

        return xRanges.fold(mutableListOf<Pair<Int, Int>>()) { acc, item ->
            if (acc.isEmpty() || acc.last().second < item.first) {
                acc.add(item)
            } else {
                val last = acc.removeLast()
                acc.add(Pair(min(item.first, last.first), max(item.second, last.second)))
            }
            acc
        }.map { IntRange(it.first, it.second) }
    }

    fun part1(input: List<Position>, row: Int): Int {
        val sensorsAffectingRow = input.filter {
            abs(it.sensorY - row) <= it.manhattanDistance()
        }

        val rangesWithoutBeacon = positionsWithoutBeaconForRow(input, row)
        val beaconsInRow = sensorsAffectingRow.filter {it.beaconY == row }.map { it.beaconX }.distinct()
            .count { beacon ->
                rangesWithoutBeacon.map { beacon in it }.any { it }
            }

        return rangesWithoutBeacon.sumOf { it.count() } - beaconsInRow
    }

    fun part2(input: List<Position>, validRange: IntRange): Long {

        var emptyX = -1
        val answerY = validRange.asSequence().find { row ->
            val rangesWithoutBeacon = positionsWithoutBeaconForRow(input, row)
            val beaconsInRow = input.filter { it.beaconY == row}.map { it.beaconX }
            rangesWithoutBeacon.map {range ->
                if ((range.first - 1) in validRange && (range.first - 1) !in beaconsInRow) {
                    emptyX = range.first - 1
                    true
                } else {
                    false
                }
            }.any { it }
        }
        println("row found at y=$answerY with empty X space at x=$emptyX")
        return emptyX * 4000000L + answerY!!
    }

    // test if implementation meets criteria from the description, like:
    //val input = readInput("Day15_test")

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

    println("part 1 solution: ${part1(coordinates, 2000000)}")
    println("part 2 solution: ${part2(coordinates, 0..4000000)}")
//    println("part 1 solution: ${part1(coordinates, 10)}")
//    println("part 2 solution: ${part2(coordinates, 0..20)}")

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

