fun main() {
    fun part1(input: List<String>): Int {
        var answer = 0
        var runningTotal = 0

        input.forEach {
            if (it.isNotEmpty()) {
                runningTotal += it.toInt()
            } else {
                answer = answer.coerceAtLeast(runningTotal)
                runningTotal = 0
            }
        }

        return answer
    }

    fun part2(input: List<String>): Int {

        val delimiters = input.mapIndexedNotNull {
            index, s -> index.takeIf { s.isEmpty() }
        }

        val groups = delimiters.mapIndexed {
            index, d -> input.subList(if (index == 0) 0 else delimiters[index-1] +1, d)
        }

        return groups.map {
            it.fold(0) { sum, str -> sum + str.toInt() }
        }.sortedDescending().take(3).sum()

    }

    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day01_test")
//    check(part1(testInput) == 1)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
