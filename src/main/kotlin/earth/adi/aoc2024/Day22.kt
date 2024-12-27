package earth.adi.aoc2024

class Day22 {

  class Secret(s: Long) {
    var value = s

    fun mix(n: Long) {
      value = n xor value
    }

    fun prune() {
      value = value % 16777216
    }

    fun next(): Long {
      mix(value shl 6)
      prune()
      mix((value shr 5))
      prune()
      mix(value shl 11)
      prune()
      return value
    }

    fun digit(): Int {
      return (value % 10).toInt()
    }

    fun changes(n: Int): Changes {
      return Changes(
          (0 until n)
              .map {
                val d = digit()
                next()
                d
              }
              .windowed(2)
              .map { Change(it[1] - it[0], it[1]) })
    }
  }

  data class Change(val diff: Int, val value: Int)

  class Changes(val changes: List<Change>) {
    val sequences =
        changes
            .map { it }
            .windowed(SEQ_LEN)
            .map { keyOf(it.map { it.diff }) to it[SEQ_LEN - 1].value }
    val sequenceToValue =
        linkedMapOf<Int, Int>().also { map ->
          sequences.forEach { map.putIfAbsent(it.first, it.second) }
        }

    fun find(key: Int): Int? {
      return sequenceToValue[key]
    }

    fun buy(key: Int): Int {
      return find(key) ?: 0
    }

    companion object {
      const val SEQ_LEN = 4

      fun keyOf(ints: List<Int>): Int {
        return ints.mapIndexed { i, n -> (n + 9) shl ((ints.size - i) * (ints.size + 1)) }.sum()
      }
    }
  }

  class Solver {
    fun solve(buyerSecrets: List<Long>): Int {
      val changes = buyerSecrets.map { Secret(it).changes(2000) }
      val priceMap = mutableMapOf<Int, Int>()
      changes.forEach { change ->
        change.sequenceToValue.forEach { (key, value) ->
          priceMap.compute(key) { _, v -> v?.plus(value) ?: value }
        }
      }
      return priceMap.values.max()
    }
  }

  companion object {
    const val INPUT_DAY_22 = "/day_22.txt"

    fun part1(inputResourceName: String): Long {
      val numbers = input(inputResourceName)
      val result =
          numbers.sumOf {
            val secret = Secret(it)
            repeat(2000) { secret.next() }
            secret.value
          }
      println("Day22 part1 = $result")
      return result
    }

    fun part2(inputResourceName: String): Int {
      val numbers = input(inputResourceName)
      val result = Solver().solve(numbers)
      println("Day22 part2 = $result")
      return result
    }

    fun input(resourceName: String): List<Long> {
      val resource =
          Any::class::class.java.getResourceAsStream(resourceName)
              ?: throw IllegalArgumentException("Resource $resourceName not found")
      return resource.bufferedReader().readLines().filter { it.isNotBlank() }.map { it.toLong() }
    }
  }
}
