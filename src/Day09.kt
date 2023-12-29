fun main() {
    fun part1(input: List<String>): Long = input.sumOf { l ->
        getDiff(l.split(" ").map { it.toLong() })
    }

    val testInput = readInput("Day09_test")
    check(part1(testInput) == 114L)

    val input = readInput("Day09")
    part1(input).println()
}

fun getDiff(input: List<Long>): Long = if(input.all { it == input[0] }){
    input[0]
} else {
    input.last() + getDiff(input.zipWithNext { first, second -> second - first })
}