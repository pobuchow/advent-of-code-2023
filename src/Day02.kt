fun main() {
    fun gameResults(input: List<String>) = input.map {
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
    }

    fun part1(input: List<String>): Int = gameResults(input).filter {
        it.second.flatten().none { subset ->
            subset["red"]?.let { red -> red > 12 } ?: false ||
                    subset["green"]?.let { green -> green > 13 } ?: false ||
                    subset["blue"]?.let { blue -> blue > 14 } ?: false
        }
    }.sumOf { it.first }

    fun part2(input: List<String>): Int = gameResults(input).sumOf { results ->
        results.second
            .flatten()
            .groupBy { color ->
                color.keys
            }.mapValues { cubes ->
                cubes.value.map { it.values }.flatten().maxOf { it }
            }.values.reduce { a, b -> a * b }
    }

    val testInput1 = readInput("Day02_test")
    check(part1(testInput1) == 8)
    check(part2(testInput1) == 2286)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
