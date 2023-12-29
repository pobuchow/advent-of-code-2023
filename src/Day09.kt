fun main() {
    fun part1(input: List<String>): Long = input.sumOf { l ->
        futureExtrapolation(l.split(" ").map { it.toLong() })
    }

    fun part2(input: List<String>): Long = input.sumOf { l ->
        pastExtrapolation(l.split(" ").map { it.toLong() })
    }

    val testInput = readInput("Day09_test")
    check(part1(testInput) == 114L)
    check(part2(testInput) == 2L)

    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}

fun futureExtrapolation(input: List<Long>): Long = if(input.all { it == input[0] }){
    input[0]
} else {
    input.last() + futureExtrapolation(input.zipWithNext { first, second -> second - first })
}

fun pastExtrapolation(input: List<Long>): Long = if(input.all { it == input[0] }){
    input[0]
} else {
    input.first() - pastExtrapolation(input.zipWithNext { first, second -> second - first })
}