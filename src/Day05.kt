fun main() {
    fun part1(input: String): Long {
        val inputGroups = input.split("\n\n")
        val dictionary = inputGroups.drop(1).map {
            val splitKeyValues = it.split(":")
            Pair(splitKeyValues[0].split(" map")[0], splitKeyValues[1].split("\n").filter{ c -> c.isNotBlank()}.map { c -> c.trim() })
        }.groupBy { it.first }.mapValues { it.value[0].second.map { l ->
            val (target, source, length) = l.split(" ").map { c -> c.toLong() }
            Triple(target, source, length)
        } }

        val seeds = inputGroups[0].split(": ")[1].split(" ").map { it.toLong() }

        fun mapWithDictionary(
            key: String,
            element: Long
        ) = dictionary[key]!!.find { m -> element in (m.second..m.third.plus(m.second).minus(1)) }?.let { m ->
            element.plus(m.first - m.second)
        } ?: element

        return seeds.asSequence().map {
            mapWithDictionary("seed-to-soil", it)
        }.map {
            mapWithDictionary("soil-to-fertilizer", it)
        }.map {
            mapWithDictionary("fertilizer-to-water", it)
        }.map {
            mapWithDictionary("water-to-light", it)
        }.map {
            mapWithDictionary("light-to-temperature", it)
        }.map {
            mapWithDictionary("temperature-to-humidity", it)
        }.minOfOrNull {
            mapWithDictionary("humidity-to-location", it)
        }?:0
    }

    fun part2(input: String): Long {
        val inputGroups = input.split("\n\n")
        val dictionary = inputGroups.drop(1).map {
            val splitKeyValues = it.split(":")
            Pair(splitKeyValues[0].split(" map")[0], splitKeyValues[1].split("\n").filter{ c -> c.isNotBlank()}.map { c -> c.trim() })
        }.groupBy { it.first }.mapValues { it.value[0].second.map { l ->
            val (target, source, length) = l.split(" ").map { c -> c.toLong() }
            Triple(target, source, length)
        } }

        val seeds = inputGroups[0].split(": ")[1].split(" ").map { it.toLong() }.chunked(2)
        val seeds2 = seeds.asSequence().map { (it[0]..(it[0].plus(it[1]).minus(1))).asSequence() }.flatten()

        fun mapWithDictionary(
            key: String,
            element: Long
        ) = dictionary[key]!!.find { m -> element in (m.second..m.third.plus(m.second).minus(1)) }?.let { m ->
            element.plus(m.first - m.second)
        } ?: element

        return seeds2.map {
            mapWithDictionary("seed-to-soil", it)
        }.map {
            mapWithDictionary("soil-to-fertilizer", it)
        }.map {
            mapWithDictionary("fertilizer-to-water", it)
        }.map {
            mapWithDictionary("water-to-light", it)
        }.map {
            mapWithDictionary("light-to-temperature", it)
        }.map {
            mapWithDictionary("temperature-to-humidity", it)
        }.minOfOrNull {
            mapWithDictionary("humidity-to-location", it)
        }?:0
    }

    val testInput1 = readInputText("Day05_test")
    check(part1(testInput1) == 35L)
    check(part2(testInput1) == 46L)

    val input = readInputText("Day05")
    part1(input).println()
    part2(input).println()
}
