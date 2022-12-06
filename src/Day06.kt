fun main() {
    fun part1(input: List<String>): Int {
        return input.first().windowedSequence(4).indexOfFirst {
            it.toSet().size == 4
        } + 4
    }

    fun part2(input: List<String>): Int {
        return input.first().windowedSequence(14).indexOfFirst {
            it.toSet().size == 14
        } + 14
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(listOf(testInput[0])) == 5)
    check(part1(listOf(testInput[1])) == 6)
    check(part1(listOf(testInput[2])) == 10)
    check(part1(listOf(testInput[3])) == 11)
    check(part2(listOf(testInput[4])) == 19)
    check(part2(listOf(testInput[5])) == 23)
    check(part2(listOf(testInput[6])) == 23)
    check(part2(listOf(testInput[7])) == 29)
    check(part2(listOf(testInput[8])) == 26)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}
