package earth.adi.aoc2024

class Day14 {

  data class Vec(val x: Int, val y: Int)

  data class Robot(var position: Vec, val velocity: Vec) {
    fun moveInside(space: Space) {
      var x = (position.x + velocity.x) % space.width
      var y = (position.y + velocity.y) % space.height
      if (x < 0) x = space.width + x
      if (y < 0) y = space.height + y
      position = Vec(x, y)
    }

    companion object {
      fun from(line: String): Robot {
        val (p, v) = line.split(" ").map { it.substringAfter("=").split(",") }
        val position = p.map { it.trim().toInt() }.let { Vec(it[0], it[1]) }
        val velocity = v.map { it.trim().toInt() }.let { Vec(it[0], it[1]) }
        return Robot(position, velocity)
      }
    }
  }

  data class Space(val width: Int, val height: Int) {
    fun print(robots: List<Robot>) {
      val grid = Array(height) { CharArray(width) { '.' } }
      robots.forEach { grid[it.position.y][it.position.x] = '#' }
      grid.forEach { println(it) }
      println()
    }

    fun safetyFactor(robots: List<Robot>): Int {
      val halfWidth = width / 2
      val halfHeight = height / 2
      val q1 = robots.count { it.position.x < halfWidth && it.position.y < halfHeight }
      val q2 = robots.count { it.position.x > halfWidth && it.position.y < halfHeight }
      val q3 = robots.count { it.position.x < halfWidth && it.position.y > halfHeight }
      val q4 = robots.count { it.position.x > halfWidth && it.position.y > halfHeight }
      return q1 * q2 * q3 * q4
    }

    fun isChristmasTreeLayout(robots: List<Robot>): Boolean {
      val positions = robots.map { it.position }.toSet()
      val l = 20
      for (x in 0 until width) {
        for (i in 0 until height - l) {
          if ((i until i + l).all { y -> positions.contains(Vec(x, y)) }) {
            return true
          }
        }
      }
      for (y in 0 until height) {
        for (i in 0 until width - l) {
          if ((i until i + l).all { x -> positions.contains(Vec(x, y)) }) {
            return true
          }
        }
      }
      return false
    }
  }

  companion object {
    const val INPUT_DAY_14 = "/day_14.txt"

    fun part1(inputResourceName: String, space: Space): Int {
      val robots = input(inputResourceName)
      repeat(100) { robots.map { it.moveInside(space) } }
      val result = space.safetyFactor(robots)
      println("Day14 part1 = $result")
      return result
    }

    fun part2(inputResourceName: String): Int {
      val robots = input(inputResourceName)
      val space = Space(101, 103)
      var s = 0
      while (true) {
        if (space.isChristmasTreeLayout(robots)) {
          space.print(robots)
          break
        }
        robots.forEach { it.moveInside(space) }
        s++
      }
      val result = s
      println("Day14 part2 = $result")
      return result
    }

    fun input(resourceName: String): List<Robot> {
      val resource =
          Any::class::class.java.getResourceAsStream(resourceName)
              ?: throw IllegalArgumentException("Resource $resourceName not found")
      return resource.bufferedReader().readLines().filter { it.isNotBlank() }.map { Robot.from(it) }
    }
  }
}
