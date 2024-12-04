package earth.adi.aoc2024

class Day04 {

  companion object {
    const val INPUT_DAY_04 = "/day_04.txt"

    private val TARGET = arrayOf('X', 'M', 'A', 'S')
    private val FLIP_TARGET = arrayOf('M', 'A', 'S')
    private val ALL_DIRECTIONS =
        // (dx, dy):
        // (-1,-1)  (0,-1)  (1,-1)
        //         \   |   /
        //  (-1,0) - (0,0) - (1,0)
        //         /   |   \
        //  (-1,1)   (0,1)   (1,1)
        listOf(
            Pair(0, -1),
            Pair(1, -1),
            Pair(1, 0),
            Pair(1, 1),
            Pair(0, 1),
            Pair(-1, 1),
            Pair(-1, 0),
            Pair(-1, -1),
        )

    fun part1(inputResourceName: String): Int {
      val input = input(inputResourceName)
      val result = search(input)
      println("Day04 part1 = $result")
      return result
    }

    fun part2(inputResourceName: String): Int {
      val input = input(inputResourceName)
      val result = searchFlip(input)
      println("Day04 part2 = $result")
      return result
    }

    fun search(input: List<List<Char>>): Int {
      var found = 0
      input.indices.forEach { y ->
        input[y].indices.forEach { x ->
          ALL_DIRECTIONS.forEach { (dx, dy) ->
            val result = searchDirection(input, x, y, dx, dy)
            if (result) {
              found++
            }
          }
        }
      }
      return found
    }

    private fun searchDirection(
        input: List<List<Char>>,
        x: Int,
        y: Int,
        dx: Int,
        dy: Int
    ): Boolean {
      for (i in TARGET.indices) {
        val nx = x + i * dx
        val ny = y + i * dy
        if (nx < 0 || nx >= input[0].size || ny < 0 || ny >= input.size) {
          return false
        }
        if (input[ny][nx] != TARGET[i]) {
          return false
        }
      }
      return true
    }

    fun searchFlip(input: List<List<Char>>): Int {
      var found = 0
      input.indices.forEach { y ->
        input[y].indices.forEach { x ->
          val result = isFlip(input, x, y)
          if (result) {
            found++
          }
        }
      }
      return found
    }

    fun isFlip(input: List<List<Char>>, x: Int, y: Int): Boolean {
      if (input[y][x] != 'A') {
        return false
      }
      if (x == 0 || x == input[0].size - 1 || y == 0 || y == input.size - 1) {
        return false
      }
      val x1 =
          (input[y - 1][x - 1] == 'M' && input[y + 1][x + 1] == 'S') ||
              (input[y - 1][x - 1] == 'S' && input[y + 1][x + 1] == 'M')

      val x2 =
          (input[y - 1][x + 1] == 'M' && input[y + 1][x - 1] == 'S') ||
              (input[y - 1][x + 1] == 'S' && input[y + 1][x - 1] == 'M')

      return x1 && x2
    }

    fun input(resourceName: String): List<List<Char>> {
      val resource =
          Any::class::class.java.getResourceAsStream(resourceName)
              ?: throw IllegalArgumentException("Resource $resourceName not found")
      return resource
          .bufferedReader()
          .readLines()
          .filter { it.isNotBlank() }
          .map { it.toCharArray().asList() }
    }
  }
}
