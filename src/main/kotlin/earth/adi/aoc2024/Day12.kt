package earth.adi.aoc2024

class Day12 {

  data class Position(val x: Int, val y: Int) {
    val up: Position
      get() = Position(x, y - 1)

    val right: Position
      get() = Position(x + 1, y)

    val down: Position
      get() = Position(x, y + 1)

    val left: Position
      get() = Position(x - 1, y)

    val neighbors: List<Position>
      get() = listOf(up, right, down, left)

    val upRight: Position
      get() = Position(x + 1, y - 1)

    val downRight: Position
      get() = Position(x + 1, y + 1)

    val upLeft: Position
      get() = Position(x - 1, y - 1)

    val downLeft: Position
      get() = Position(x - 1, y + 1)
  }

  data class GardenMap(val data: List<List<Char>>) {
    fun at(position: Position): Char {
      return if (isInside(position)) {
        data[position.y][position.x]
      } else {
        '.'
      }
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

    fun cost(mapped: Map<Char, List<Area>>): Int {
      val areas = mapped.values.flatten()
      return areas.sumOf { it.cost() }
    }

    fun discountCost(mapped: Map<Char, List<Area>>): Int {
      val areas = mapped.values.flatten()
      return areas.sumOf { it.discountCost() }
    }

    fun map(): Map<Char, List<Area>> {
      val mappedPositions = mutableSetOf<Position>()
      val map = mutableMapOf<Char, List<Area>>()
      for (x in 0 until width) {
        for (y in 0 until height) {
          val position = Position(x, y)
          if (!mappedPositions.contains(position)) {
            val area = discoverArea(position)
            mappedPositions.addAll(area.data)
            map.compute(area.name) { _, v ->
              if (v == null) {
                listOf(area)
              } else {
                v + area
              }
            }
          }
        }
      }
      return map
    }

    fun discoverArea(position: Position): Area {
      val name = at(position)
      val discovered = mutableSetOf<Position>(position)
      var front: Set<Position> = setOf(position)
      while (true) {
        val newPositions =
            front
                .flatMap {
                  it.neighbors.mapNotNull { newPosition ->
                    if (isInside(newPosition) &&
                        at(newPosition) == name &&
                        !discovered.contains(newPosition)) {
                      newPosition
                    } else {
                      null
                    }
                  }
                }
                .toSet()
        if (newPositions.isEmpty()) break
        discovered.addAll(newPositions)
        front = newPositions
      }
      return Area(this, name, discovered)
    }

    fun cornerCount(position: Position): Int {
      var count = 0
      val name = at(position)
      if (at(position.up) != name && at(position.right) != name) count++
      if (at(position.up) == name && at(position.right) == name && at(position.upRight) != name)
          count++

      if (at(position.right) != name && at(position.down) != name) count++
      if (at(position.right) == name && at(position.down) == name && at(position.downRight) != name)
          count++

      if (at(position.down) != name && at(position.left) != name) count++
      if (at(position.down) == name && at(position.left) == name && at(position.downLeft) != name)
          count++

      if (at(position.left) != name && at(position.up) != name) count++
      if (at(position.left) == name && at(position.up) == name && at(position.upLeft) != name)
          count++

      return count
    }

    companion object {
      fun from(text: String): GardenMap {
        val data = text.lines().filter { it.isNotBlank() }.map { line -> line.map { it } }
        return GardenMap(data)
      }
    }
  }

  data class Area(val gardenMap: GardenMap, val name: Char, val data: Set<Position>) {
    fun cost(): Int {
      return size * perimeter()
    }

    val size: Int
      get() = data.size

    fun perimeter(): Int {
      return data
          .flatMap {
            it.neighbors.map { boundaryPosition ->
              if (!gardenMap.isInside(boundaryPosition)) {
                1
              } else if (gardenMap.at(boundaryPosition) != name) {
                1
              } else {
                0
              }
            }
          }
          .sum()
    }

    fun discountCost(): Int {
      return size * sides()
    }

    fun sides(): Int {
      return corners()
    }

    fun corners(): Int {
      return data.sumOf { gardenMap.cornerCount(it) }
    }
  }

  companion object {
    const val INPUT_DAY_12 = "/day_12.txt"

    fun part1(inputResourceName: String): Long {
      val gardenMap = input(inputResourceName)
      val result = gardenMap.cost(gardenMap.map()).toLong()
      println("Day12 part1 = $result")
      return result
    }

    fun part2(inputResourceName: String): Long {
      val gardenMap = input(inputResourceName)
      val result = gardenMap.discountCost(gardenMap.map()).toLong()
      println("Day12 part2 = $result")
      return result
    }

    fun input(resourceName: String): GardenMap {
      val resource =
          Any::class::class.java.getResourceAsStream(resourceName)
              ?: throw IllegalArgumentException("Resource $resourceName not found")
      return GardenMap.from(resource.bufferedReader().readText().trim())
    }
  }
}
