package earth.adi.aoc2024

class Day25 {

  enum class SchematicType {
    LOCK,
    KEY
  }

  data class Schematic(val type: SchematicType, val heights: List<Int>, val size: Int) {
    fun fit(other: Schematic): Boolean {
      if (type == other.type) throw IllegalArgumentException("Cannot fit same type")
      return heights.zip(other.heights).all { (a, b) -> a + b <= size }
    }

    companion object {
      fun parse(text: String): Schematic {
        val data = text.lines().map { it.toList() }
        val type =
            if (data[0].all { it == '#' }) {
              SchematicType.LOCK
            } else {
              SchematicType.KEY
            }
        val height = data.size
        val width = data[0].size
        val heights =
            (0 until width).map { x -> (0 until height).count { y -> data[y][x] == '#' } - 1 }
        val size = height - 2
        return Schematic(type, heights, size)
      }
    }
  }

  companion object {
    const val INPUT_DAY_25 = "/day_25.txt"

    fun part1(inputResourceName: String): Int {
      val schematics = input(inputResourceName)
      val keys = schematics.filter { it.type == SchematicType.KEY }
      val locks = schematics.filter { it.type == SchematicType.LOCK }

      val result = keys.sumOf { key -> locks.count { lock -> key.fit(lock) } }

      println("Day25 part1 = $result")
      return result
    }

    fun part2(inputResourceName: String): Long {
      val schematics = input(inputResourceName)
      val result = 0L
      println("Day25 part2 = $result")
      return result
    }

    fun input(resourceName: String): List<Schematic> {
      val resource =
          Any::class::class.java.getResourceAsStream(resourceName)
              ?: throw IllegalArgumentException("Resource $resourceName not found")
      return resource.bufferedReader().readText().trim().split(Regex("\\n\\s*\\n")).map {
        Schematic.parse(it)
      }
    }
  }
}
