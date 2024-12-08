package earth.adi.aoc2024

class Day08 {

  data class Position(val x: Int, val y: Int)

  data class AntennaMap(val data: List<List<Char>>) {
    fun isInside(position: Position): Boolean {
      with(position) {
        if (y < 0) return false
        if (y >= height) return false
        if (x < 0) return false
        if (x >= width) return false
      }
      return true
    }

    val height: Int
      get() = data.size

    val width: Int
      get() = data[0].size

    fun at(pos: Position): Char {
      return data[pos.y][pos.x]
    }

    fun collectAntennas(): Map<Char, List<Position>> {
      val list = mutableListOf<Pair<Char, Position>>()
      for (x in 0 until width) {
        for (y in 0 until height) {
          val pos = Position(x, y)
          val c = at(pos)
          if (c != SPACE) {
            list.add(c to pos)
          }
        }
      }
      return list.groupBy({ it.first }, { it.second })
    }

    fun projectAntiNodes(antennas: List<Position>): List<Position> {
      val list = mutableListOf<Position>()
      for (i in 1 until antennas.size) {
        for (j in 0 until i) {
          val a1 = antennas[i]
          val a2 = antennas[j]
          val p1 = Position(a1.x - (a2.x - a1.x), a1.y - (a2.y - a1.y))
          if (isInside(p1)) {
            list.add(p1)
          }
          val p2 = Position(a2.x - (a1.x - a2.x), a2.y - (a1.y - a2.y))
          if (isInside(p2)) {
            list.add(p2)
          }
        }
      }
      return list
    }

    fun projectAntiNodesWithHarmonics(antennas: List<Position>): List<Position> {
      val list = mutableListOf<Position>()
      for (i in 1 until antennas.size) {
        for (j in 0 until i) {
          val a1 = antennas[i]
          val a2 = antennas[j]
          var n = 0
          while (true) {
            val p1 = Position(a1.x - n * (a2.x - a1.x), a1.y - n * (a2.y - a1.y))
            if (isInside(p1)) {
              list.add(p1)
              n++
            } else {
              break
            }
          }
          n = 0
          while (true) {
            val p2 = Position(a2.x - n * (a1.x - a2.x), a2.y - n * (a1.y - a2.y))
            if (isInside(p2)) {
              list.add(p2)
              n++
            } else {
              break
            }
          }
        }
      }
      return list
    }

    companion object {
      const val SPACE = '.'

      fun from(text: String): AntennaMap {
        val data = text.split("\n").map { it.toCharArray().toList() }
        return AntennaMap(data)
      }
    }
  }

  companion object {
    const val INPUT_DAY_08 = "/day_08.txt"

    fun part1(inputResourceName: String): Int {
      val antennaMap = input(inputResourceName)
      val result =
          antennaMap
              .collectAntennas()
              .flatMap { e -> antennaMap.projectAntiNodes(e.value) }
              .toSet()
              .size
      println("Day08 part1 = $result")
      return result
    }

    fun part2(inputResourceName: String): Int {
      val antennaMap = input(inputResourceName)
      val result =
          antennaMap
              .collectAntennas()
              .flatMap { e -> antennaMap.projectAntiNodesWithHarmonics(e.value) }
              .toSet()
              .size
      println("Day08 part2 = $result")
      return result
    }

    fun input(resourceName: String): AntennaMap {
      val resource =
          Any::class::class.java.getResourceAsStream(resourceName)
              ?: throw IllegalArgumentException("Resource $resourceName not found")
      val text = resource.bufferedReader().readText().trim()
      return AntennaMap.from(text)
    }
  }
}
