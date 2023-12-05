import kotlin.math.pow

fun main() {
    fun part1(input: List<String>): Int = game(input).map { rewards(it) }.filter { it > 0 }
        .map { 2.0.pow((it - 1).toDouble()) }.sum().toInt()

    fun part2(input: List<String>): Int {
        val rewards = game(input).associateBy { it.first }.mapValues { rewards(it.value) }.mapValues {
            val toList = (it.key + 1..it.key + it.value).toMutableList()
            toList.add(0)
            toList
        }
        val cardsWithReward = rewards.filter { it.value.isNotEmpty() }.keys.toSet()

        return calc(game(input).toList().map { it.first }, cardsWithReward, rewards)
    }

    val testInput1 = readInput("Day04_test")
    check(part1(testInput1) == 13)
    check(part2(testInput1) == 30)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}

fun game(input: List<String>) = input.asSequence().map {
    val splitGameIdAndNumbers = it.split(":")
    val splitNumbersAndWinningResult = splitGameIdAndNumbers[1].split("|")
    Triple(
        splitGameIdAndNumbers[0].filter { c -> c.isDigit() }.toInt(),
        splitNumbersAndWinningResult[0].split(" ").filter { s -> s.isNotBlank() },
        splitNumbersAndWinningResult[1].split(" ").filter { s -> s.isNotBlank() },
    )
}

fun rewards(it: Triple<Int, List<String>, List<String>>) =
    it.second.filter { num -> it.third.contains(num) }.size

fun calc(nums: List<Int>, cardsWithReward: Set<Int>, rewards: Map<Int, List<Int>>): Int =
    if (nums.any { it in cardsWithReward }) {
        val newNums = nums.map { rewards[it] ?: listOf(0) }.flatten()
        calc(newNums, cardsWithReward, rewards)
    } else {
        nums.size
    }
