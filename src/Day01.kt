fun main() {
    fun part1(input: List<String>): Int = input.sumOf {
        it.filter {
            c -> c.isDigit()
        }.let { digits ->
            if(digits.isNotBlank()){
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
        val input1 = input.map {
            val last = it.findLastAnyOf(wordToDigitMap.keys)
            val first = it.findAnyOf(wordToDigitMap.keys)
            first?.let { it1 ->
                it.replaceRange(it1.first, it1.first+1, wordToDigitMap[first.second]!!).let { it2 ->
                    last?.let { it3 ->
                        it2.replaceRange(it3.first, it3.first+1, wordToDigitMap[last.second]!!)
                    } ?: it2
            } } ?: it
        }
        return part1(input1)
    }

    val testInput1 = readInput("Day01_1_test")
    check(part1(testInput1) == 142)

    val testInput2 = readInput("Day01_2_test")
    check(part2(testInput2) == 281)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
