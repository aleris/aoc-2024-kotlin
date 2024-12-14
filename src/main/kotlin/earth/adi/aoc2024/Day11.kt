package earth.adi.aoc2024

class Day11 {

  companion object {
    const val INPUT_DAY_11 = "/day_11.txt"

    fun part1(inputResourceName: String): Long {
      val input = input(inputResourceName)
      //      val list = input.toMutableList()
      //      repeat(25) { blink(list) }
      //      val result = list.size

      var map = input.associate { it to 1L }
      repeat(25) { map = blink(map) }
      val result = map.values.sum()

      println("Day11 part1 = $result")
      return result
    }

    fun blink(list: MutableList<Long>) {
      val iterator = list.listIterator()
      for (i in iterator) {
        if (i == 0L) {
          iterator.remove()
          iterator.add(1)
          continue
        }

        val s = i.toString()
        if (s.length % 2 == 0) {
          iterator.remove()
          val i1 = s.substring(0, s.length / 2).toLong()
          val i2 = s.substring(s.length / 2).toLong()
          iterator.add(i1)
          iterator.add(i2)
          continue
        }

        iterator.remove()
        iterator.add(i * 2024)
      }
    }

    fun blink(map: Map<Long, Long>): Map<Long, Long> {
      val newMap = mutableMapOf<Long, Long>()

      fun add(n: Long, count: Long) {
        newMap.compute(n) { _, value -> (value ?: 0L) + count }
      }

      for ((k, count) in map) {
        if (k == 0L) {
          add(1, count)
          continue
        }

        val s = k.toString()
        if (s.length % 2 == 0) {
          val i1 = s.substring(0, s.length / 2).toLong()
          val i2 = s.substring(s.length / 2).toLong()
          add(i1, count)
          add(i2, count)
          continue
        }

        add(k * 2024, count)
      }

      return newMap
    }

    fun part2(inputResourceName: String): Long {
      val input = input(inputResourceName)
      var map = input.associate { it to 1L }
      repeat(75) { map = blink(map) }
      val result = map.values.sum()
      println("Day11 part2 = $result")
      return result
    }

    fun input(resourceName: String): List<Long> {
      val resource =
          Any::class::class.java.getResourceAsStream(resourceName)
              ?: throw IllegalArgumentException("Resource $resourceName not found")
      return resource.bufferedReader().readText().trim().split(" ").map { it.toLong() }
    }
  }
}
