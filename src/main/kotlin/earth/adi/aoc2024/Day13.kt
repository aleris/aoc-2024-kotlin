package earth.adi.aoc2024

class Day13 {
  data class Vec(val x: Long, val y: Long)

  data class ClawMachine(val buttonA: Vec, val buttonB: Vec, val prize: Vec) {
    fun solve(): Vec? {
      val ax = buttonA.x
      val ay = buttonA.y
      val bx = buttonB.x
      val by = buttonB.y
      val px = prize.x
      val py = prize.y

      val sx = (px * by - bx * py) / (ax * by - bx * ay)
      val sb = (px * ay - ax * py) / (ay * bx - ax * by)

      if (ax * sx + bx * sb != px || ay * sx + by * sb != py) {
        return null // no solution
      }
      return Vec(sx, sb)
    }

    fun corrected(): ClawMachine {
      return ClawMachine(buttonA, buttonB, Vec(10000000000000 + prize.x, 10000000000000 + prize.y))
    }

    companion object {
      fun from(lines: List<String>): ClawMachine {
        val (a, b, p) = lines.map { it.substringAfter(": ") }
        val buttonA =
            a.split(",").map { it.substringAfter("+").trim().toLong() }.let { Vec(it[0], it[1]) }
        val buttonB =
            b.split(",").map { it.substringAfter("+").trim().toLong() }.let { Vec(it[0], it[1]) }
        val prize =
            p.split(",").map { it.substringAfter("=").trim().toLong() }.let { Vec(it[0], it[1]) }
        return ClawMachine(buttonA, buttonB, prize)
      }

      fun cost(solution: Vec): Long {
        return solution.x * 3 + solution.y
      }
    }
  }

  companion object {
    const val INPUT_DAY_13 = "/day_13.txt"

    fun part1(inputResourceName: String): Long {
      val input = input(inputResourceName)
      val result = input.mapNotNull { it.solve() }.sumOf { ClawMachine.cost(it) }
      println("Day13 part1 = $result")
      return result
    }

    fun part2(inputResourceName: String): Long {
      val input = input(inputResourceName)
      val result = input.mapNotNull { it.corrected().solve() }.sumOf { ClawMachine.cost(it) }
      println("Day13 part2 = $result")
      return result
    }

    fun input(resourceName: String): List<ClawMachine> {
      val resource =
          Any::class::class.java.getResourceAsStream(resourceName)
              ?: throw IllegalArgumentException("Resource $resourceName not found")
      return resource.bufferedReader().readLines().chunked(4).map {
        ClawMachine.from(it.filter { it.isNotBlank() })
      }
    }
  }
}
