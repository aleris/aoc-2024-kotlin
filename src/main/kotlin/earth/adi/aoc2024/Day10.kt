package earth.adi.aoc2024

class Day10 {
  data class Position(val x: Int, val y: Int) {
    fun plus(direction: Direction): Position {
      return Position(x + direction.x, y + direction.y)
    }
  }

  enum class Direction(val x: Int, val y: Int) {
    UP(0, -1),
    RIGHT(1, 0),
    DOWN(0, 1),
    LEFT(-1, 0),
  }

  data class TopoMap(val data: List<List<Int>>) {
    fun at(position: Position): Int {
      return data[position.y][position.x]
    }

    val height: Int
      get() = data.size

    val width: Int
      get() = data[0].size

    fun isInside(position: Position): Boolean {
      with(position) {
        if (y < 0) return false
        if (y >= height) return false
        if (x < 0) return false
        if (x >= width) return false
      }
      return true
    }

    fun print() {
      data.forEach { row ->
        row.forEach { cell ->
          print(
              when (cell) {
                -1 -> '.'
                else -> cell
              })
        }
        println()
      }
      println()
    }

    fun candidateTrailheads(): List<Position> {
      return data.flatMapIndexed { y, row ->
        row.mapIndexedNotNull { x, cell ->
          if (cell == 0) {
            Position(x, y)
          } else {
            null
          }
        }
      }
    }

    fun peaksFrom(position: Position): Set<Position> {
      val value = at(position)
      return Direction.entries
          .flatMap { direction ->
            val nextPosition = position.plus(direction)
            if (isInside(nextPosition)) {
              val nextValue = at(nextPosition)
              if (nextValue == value + 1) {
                if (nextValue == 9) {
                  setOf(nextPosition)
                } else {
                  peaksFrom(nextPosition)
                }
              } else {
                setOf()
              }
            } else {
              setOf()
            }
          }
          .toSet()
    }

    fun rating(position: Position): Int {
      val value = at(position)
      return Direction.entries.sumOf { direction ->
        val nextPosition = position.plus(direction)
        if (isInside(nextPosition)) {
          val nextValue = at(nextPosition)
          if (nextValue == value + 1) {
            if (nextValue == 9) {
              1
            } else {
              rating(nextPosition)
            }
          } else {
            0
          }
        } else {
          0
        }
      }
    }

    companion object {
      fun from(text: String): TopoMap {
        val data =
            text
                .lines()
                .filter { it.isNotBlank() }
                .map { line ->
                  line.map { char ->
                    when (char) {
                      '.' -> -1
                      else -> char.digitToInt()
                    }
                  }
                }
        return TopoMap(data)
      }
    }
  }

  companion object {
    const val INPUT_DAY_10 = "/day_10.txt"

    fun part1(inputResourceName: String): Int {
      val topoMap = input(inputResourceName)
      val result = topoMap.candidateTrailheads().sumOf { topoMap.peaksFrom(it).size }
      println("Day10 part1 = $result")
      return result
    }

    fun part2(inputResourceName: String): Int {
      val diskMap = input(inputResourceName)
      val result = diskMap.candidateTrailheads().sumOf { diskMap.rating(it) }
      println("Day10 part2 = $result")
      return result
    }

    fun input(resourceName: String): TopoMap {
      val resource =
          Any::class::class.java.getResourceAsStream(resourceName)
              ?: throw IllegalArgumentException("Resource $resourceName not found")
      val text = resource.bufferedReader().readText().trim()
      return TopoMap.from(text)
    }
  }
}
