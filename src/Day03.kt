fun main() {
    fun part1(input: List<String>): Int = sumElements(symbols(input), input)

    fun part2(input:List<String>): Int =
        gearRatio(symbols(input).filter{ s -> s.second.map { it.first }.contains('*') }, input)

    val testInput1 = readInput("Day03_test")
    check(part1(testInput1) == 4361)
    check(part2(testInput1) == 467835)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}

fun symbols(input: List<String>) = input.asSequence().mapIndexed { index, schemeRow ->
    schemeRow.mapIndexed { innerIndex, symbol ->
        Triple(symbol, index, innerIndex)
    }.filter { it.first.isDigit() }
}.filter { it.isNotEmpty() }.flatten().map {
    Pair(it, arrayOf(
        if (it.second > 0 && it.third > 0) Pair(
            input[it.second - 1][it.third - 1],
            Pair(it.second - 1, it.third - 1)
        ) else null,
        if (it.second > 0) Pair(input[it.second - 1][it.third], Pair(it.second - 1, it.third)) else null,
        if (it.second > 0 && input[it.second].length - 1 > it.third) Pair(
            input[it.second - 1][it.third + 1],
            Pair(it.second - 1, it.third + 1)
        ) else null,
        if (it.third > 0) Pair(input[it.second][it.third - 1], Pair(it.second, it.third - 1)) else null,
        if (input[it.second].length - 1 > it.third) Pair(
            input[it.second][it.third + 1],
            Pair(it.second, it.third + 1)
        ) else null,
        if (input.size - 1 > it.second && it.third > 0) Pair(
            input[it.second + 1][it.third - 1],
            Pair(it.second + 1, it.third - 1)
        ) else null,
        if (input.size - 1 > it.second) Pair(
            input[it.second + 1][it.third],
            Pair(it.second + 1, it.third)
        ) else null,
        if (input.size - 1 > it.second && input[it.second].length - 1 > it.third) Pair(
            input[it.second + 1][it.third + 1],
            Pair(it.second + 1, it.third + 1)
        ) else null
    ).filterNotNull().filterNot { c -> c.first.isDigit() || c.first == '.' })
}

fun sumElements(
    symbols: Sequence<Pair<Triple<Char, Int, Int>, List<Pair<Char, Pair<Int, Int>>>>>,
    input: List<String>
) = symbols.filter { it.second.isNotEmpty() }.map { it.first }.toList().map {
    part(startElement(input, it), input, it)
}.toSet().sumOf { it.first }

fun gearRatio(
    symbols: Sequence<Pair<Triple<Char, Int, Int>, List<Pair<Char, Pair<Int, Int>>>>>,
    input: List<String>
): Int = symbols.groupBy { it.second }.values.map {
    it.asSequence().map { el -> el.first }.map { p ->
        part(startElement(input, p), input, p)
    }.toSet().map { sp -> sp.first }
}.filter { it.size > 1 }.sumOf { it.reduce { a, b -> a * b } }

private fun part(
    start: String,
    input: List<String>,
    p: Triple<Char, Int, Int>
) = Pair(
    (start + input[p.second].substring(p.third).split(Regex("[^0-9]"))[0]).toInt(),
    Pair(p.second, p.third - start.length)
)

private fun startElement(
    input: List<String>,
    p: Triple<Char, Int, Int>
) = input[p.second].substring(0, p.third).reversed().split(Regex("[^0-9]"))[0].reversed()