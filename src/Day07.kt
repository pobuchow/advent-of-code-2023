fun main() {
    fun part1(input: List<String>): Long {
        val str = listOf('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2')
        val result = input.map { it.split(" ") }.map { Pair(it[0], it[1].toLong()) }.map {
            Pair(
                it,
                when {
                    it.first.none { c -> c != it.first[0] } -> "Five-of-a-kind"
                    it.first.groupBy { c -> c }.map { c -> c.value.size }.contains(4) -> "Four-of-a-kind"
                    it.first.groupBy { c -> c }.map { c -> c.value.size }.contains(3) && it.first.groupBy { c -> c }
                        .map { c -> c.value.size }.contains(2) -> "Full-house"
                    it.first.groupBy { c -> c }.map { c -> c.value.size }.contains(3) -> "Three-of-a-kind"
                    it.first.groupBy { c -> c }.map { c -> c.value.size }
                        .filter { s -> s == 2 }.size == 2 -> "Two-pairs"
                    it.first.groupBy { c -> c }.map { c -> c.value.size }.contains(2) -> "One-pair"
                    else -> "High card"
                }
            )
        }.groupBy { it.second }.mapValues { v -> v.value.map { c -> c.first } }
            .mapValues {
                it.value.sortedWith(java.util.Comparator { a, b ->
                    for (i in a.first.indices) {
                        val str1 = str.indexOf(a.first[i])
                        val str2 = str.indexOf(b.first[i])
                        if (str1 < str2) return@Comparator -1
                        if (str1 > str2) return@Comparator 1
                    }
                    return@Comparator 0
                })
            }

        return arrayListOf(
            result["Five-of-a-kind"],
            result["Four-of-a-kind"],
            result["Full-house"],
            result["Three-of-a-kind"],
            result["Two-pairs"],
            result["One-pair"],
            result["High card"])
            .filterNotNull()
            .flatten()
            .reversed()
            .mapIndexed { i, h ->
                (i + 1) * h.second
            }
            .sum()
    }

    val testInput1 = readInput("Day07_test")
    check(part1(testInput1) == 6440L)

    val input = readInput("Day07")
    part1(input).println()
}