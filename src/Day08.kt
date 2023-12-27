fun main() {
    fun part1(input: List<String>): Int {
        val instructions = input.first().toList()
        val network = input.drop(2).associate {
            val (node, lr) = it.split(" = ")
            val (l, r) = lr.removePrefix("(").removeSuffix(")").split(", ")
            node to Pair(l, r)
        }

        tailrec fun moveStep(startingPoint: String?, step: Int): Int = if (startingPoint.equals("ZZZ")) {
            step;
        } else {
            moveStep(
                when (instructions[step % instructions.size]) {
                    'L' -> network[startingPoint]!!.first
                    'R' -> network[startingPoint]!!.second
                    else -> null
                }, step + 1
            )
        }

        return moveStep("AAA", 0)
    }

    val testInput1 = readInput("Day08_test1")
    val testInput2 = readInput("Day08_test2")
    check(part1(testInput1) == 2)
    check(part1(testInput2) == 6)

    val input = readInput("Day08")
    part1(input).println()
}