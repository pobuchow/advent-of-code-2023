fun main() {
    fun part1(input: List<String>): Int = input.sumOf {
        it.filter { c ->
            c.isDigit()
        }.let { digits ->
            if (digits.isNotBlank()) {
                digits.first().digitToInt() * 10 + digits.last().digitToInt()
            } else {
                0
            }
        }
    }

    fun part2(input: List<String>): Int {
        val wordToDigitMap = mapOf(
            "one" to "1",
            "two" to "2",
            "three" to "3",
            "four" to "4",
            "five" to "5",
            "six" to "6",
            "seven" to "7",
            "eight" to "8",
            "nine" to "9"
        )
        return part1(input.map {
            it.findAnyOf(wordToDigitMap.keys)?.let { first ->
                it.replaceRange(first.first, first.first + 1, wordToDigitMap[first.second]!!).let { result ->
                    it.findLastAnyOf(wordToDigitMap.keys)?.let { last ->
                        result.replaceRange(last.first, last.first + 1, wordToDigitMap[last.second]!!)
                    } ?: result
                }
            } ?: it
        })
    }

    val testInput1 = readInput("Day01_1_test")
    check(part1(testInput1) == 142)

    val testInput2 = readInput("Day01_2_test")
    check(part2(testInput2) == 281)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
