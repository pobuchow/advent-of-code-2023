fun main() {

    fun part1(input: List<String>): Int = input.asSequence().mapIndexed { index, schemeRow ->
        schemeRow.mapIndexed { innerIndex, symbol ->
            Triple(symbol, index, innerIndex)
        }.filter { it.first.isDigit() }
    }.filter { it.isNotEmpty() }.flatten().map {
        Pair(it, arrayOf(
            if (it.second > 0 && it.third > 0) input[it.second - 1][it.third - 1] else null,
            if (it.second > 0) input[it.second - 1][it.third] else null,
            if (it.second > 0 && input[it.second].length - 1 > it.third) input[it.second - 1][it.third + 1] else null,
            if (it.third > 0) input[it.second][it.third - 1] else null,
            if (input[it.second].length - 1 > it.third) input[it.second][it.third + 1] else null,
            if (input.size - 1 > it.second && it.third > 0) input[it.second + 1][it.third - 1] else null,
            if (input.size - 1 > it.second) input[it.second + 1][it.third] else null,
            if (input.size - 1 > it.second && input[it.second].length - 1 > it.third) input[it.second + 1][it.third + 1] else null
        ).filterNotNull().filterNot { c -> c.isDigit() || c == '.' })
    }.filter { it.second.isNotEmpty() }.map { it.first }.toList().map {
        val start =
            input[it.second].substring(0, it.third).reversed().split(Regex("[^0-9]"))[0].reversed()
        Pair((start + input[it.second].substring(it.third).split(Regex("[^0-9]"))[0]).toInt(), Pair(it.second, it.third - start.length))
    }.toSet().sumOf { it.first }

    val testInput1 = readInput("Day03_test")
    check(part1(testInput1) == 4361)

    val input = readInput("Day03")
    part1(input).println()
}