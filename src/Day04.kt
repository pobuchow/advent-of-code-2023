import kotlin.math.pow

fun main() {
    fun part1(input: List<String>): Int = input.asSequence().map {
        val splitGameIdAndNumbers = it.split(":")
        val splitNumbersAndWinningResult = splitGameIdAndNumbers[1].split("|")
        Triple(
            splitGameIdAndNumbers[0].filter { c -> c.isDigit() },
            splitNumbersAndWinningResult[0].split(" ").filter { s -> s.isNotBlank() },
            splitNumbersAndWinningResult[1].split(" ").filter { s -> s.isNotBlank() },
        )
    }.map { it.second.filter { num -> it.third.contains(num) }.size }.filter { it > 0 }
        .map { 2.0.pow((it - 1).toDouble()) }.sum().toInt()

    val testInput1 = readInput("Day04_test")
    check(part1(testInput1) == 13)

    val input = readInput("Day04")
    part1(input).println()
}
