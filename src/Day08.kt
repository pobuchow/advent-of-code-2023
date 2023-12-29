fun main() {

    fun part1(input: List<String>): Long = moveStep("AAA", { it == "ZZZ" }, 0, input.first().toList(), getNetwork(input))
    fun part2(input: List<String>): Long {
        val network = getNetwork(input)
        return network.filterKeys { it.endsWith("A") }.map { entry ->
            moveStep(entry.key, { it.last() == 'Z' }, 0, input.first().toList(), network)
        }.reduce { a, b -> (a * b) / gcd(a, b) }
    }

    val testInput1 = readInput("Day08_test1")
    val testInput2 = readInput("Day08_test2")
    val testInput3 = readInput("Day08_test3")
    check(part1(testInput1) == 2L)
    check(part1(testInput2) == 6L)
    check(part2(testInput3) == 6L)

    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}

fun getNetwork(input: List<String>) = input.drop(2).associate {
    val (node, lr) = it.split(" = ")
    val (l, r) = lr.removePrefix("(").removeSuffix(")").split(", ")
    node to Pair(l, r)
}

tailrec fun moveStep(
    startingPoint: String?,
    endCondition: (String) -> Boolean,
    step: Long,
    instructions: List<Char>,
    network: Map<String, Pair<String, String>>
): Long {
    return if (startingPoint?.let { endCondition(it) } == true) {
        step;
    } else {
        moveStep(
            when (instructions[step.mod(instructions.size)]) {
                'L' -> network[startingPoint]!!.first
                'R' -> network[startingPoint]!!.second
                else -> null
            }, endCondition, step + 1, instructions, network
        )
    }
}

tailrec fun gcd(x: Long, y: Long): Long = if (y == 0L) x else gcd(y, x % y)