fun main() {
    fun part1(input: List<String>): Int {
        val timeSeries = input[0].split(":")[1].split(" ").filter { it.isNotBlank() }.map { it.toLong() }
        val distanceSeries = input[1].split(":")[1].split(" ").filter { it.isNotBlank() }.map { it.toLong() }
        return calc(timeSeries, distanceSeries)
    }

    val testInput1 = readInput("Day06_test")
    check(part1(testInput1) == 288)

    val input = readInput("Day06")
    part1(input).println()
}

fun calc(
    timeSeries: List<Long>,
    distanceSeries: List<Long>
) = timeSeries.zip(distanceSeries).map { (time, distance) ->
    (0..time).toList().filter { t -> t * (time - t) > distance }
}.map {
    it.size
}.reduce(Int::times)
