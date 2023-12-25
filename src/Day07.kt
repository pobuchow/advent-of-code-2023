fun main() {
    fun part1(input: List<String>): Long =
        calc(
            sortHands(
                getHands(input),
                listOf('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2')
            )
        )

    fun part2(input: List<String>): Long =
        calc(
            sortHands(
                getHandsWithJokers(input),
                listOf('A', 'K', 'Q', 'T', '9', '8', '7', '6', '5', '4', '3', '2', 'J')
            )
        )

    val testInput1 = readInput("Day07_test")
    check(part1(testInput1) == 6440L)
    check(part2(testInput1) == 5905L)

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}

fun calc(result: Map<String, List<Pair<String, Long>>>) =
    arrayListOf(
        result["Five-of-a-kind"],
        result["Four-of-a-kind"],
        result["Full-house"],
        result["Three-of-a-kind"],
        result["Two-pairs"],
        result["One-pair"],
        result["High card"]
    )
        .filterNotNull()
        .flatten()
        .reversed()
        .mapIndexed { i, h ->
            (i + 1) * h.second
        }
        .sum()

fun getHands(input: List<String>) =
    input.map { it.split(" ") }.map { Pair(it[0], it[1].toLong()) }.map {
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

fun getHandsWithJokers(input: List<String>) =
    input.map { it.split(" ") }.map { Pair(it[0], it[1].toLong()) }.map {
        Pair(
            it,
            when {
                it.first.none { c -> c != it.first[0] } -> "Five-of-a-kind"
                it.first.groupBy { c -> c }.map { c -> c.value.size }.contains(4) -> {
                    if (it.first.contains("J")) "Five-of-a-kind" else "Four-of-a-kind"
                }

                it.first.groupBy { c -> c }.map { c -> c.value.size }.contains(3) && it.first.groupBy { c -> c }
                    .map { c -> c.value.size }.contains(2) -> {
                    if (it.first.contains("J")) "Five-of-a-kind" else "Full-house"
                }

                it.first.groupBy { c -> c }.map { c -> c.value.size }.contains(3) -> {
                    when (it.first.count { c -> c == 'J' }) {
                        1 -> "Four-of-a-kind"
                        2 -> "Five-of-a-kind"
                        3 -> "Four-of-a-kind"
                        else -> "Three-of-a-kind"
                    }
                }

                it.first.groupBy { c -> c }.map { c -> c.value.size }
                    .filter { s -> s == 2 }.size == 2 -> {
                    when (it.first.count { c -> c == 'J' }) {
                        1 -> "Full-house"
                        2 -> "Four-of-a-kind"
                        else -> "Two-pairs"
                    }
                }

                it.first.groupBy { c -> c }.map { c -> c.value.size }.contains(2) -> {
                    when (it.first.count { c -> c == 'J' }) {
                        1 -> "Three-of-a-kind"
                        2 -> "Three-of-a-kind"
                        else -> "One-pair"
                    }
                }

                else -> {
                    if (it.first.contains("J")) "One-pair" else "High card"
                }
            }
        )
    }.groupBy { it.second }.mapValues { v -> v.value.map { c -> c.first } }

fun sortHands(
    hands: Map<String, List<Pair<String, Long>>>,
    str: List<Char>
) = hands
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
