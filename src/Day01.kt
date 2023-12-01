fun main() {
    fun part1(input: List<String>): Int = input.sumOf {
        it.filter { c -> c.isDigit() }.let { digits -> digits.first().digitToInt() * 10 + digits.last().digitToInt() }
    }

    val testInput = readInput("Day01_test")
    check(part1(testInput) == 142)

    val input = readInput("Day01")
    part1(input).println()
}
