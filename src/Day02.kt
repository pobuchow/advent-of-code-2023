fun main() {
    fun part1(input: List<String>): Int = input.map {
        val splitGameId = it.split(":")
        Pair(splitGameId[0].split(" ")[1].toInt(), splitGameId[1].split(";")
            .map { round ->
                round.trim().split(",").map { turn ->
                    val turnResult = turn.trim().split(" ")
                    mapOf(
                        turnResult[1] to turnResult[0].toInt()
                    )
                }
            })
    }.filter {
        it.second.flatten().none { subset ->
            subset["red"]?.let { red -> red > 12 } ?: false ||
                    subset["green"]?.let { green -> green > 13 } ?: false ||
                    subset["blue"]?.let { blue -> blue > 14 } ?: false
        }
    }.sumOf { it.first }

    val testInput1 = readInput("Day02_1_test")
    check(part1(testInput1) == 8)

    val input = readInput("Day02")
    part1(input).println()
}
