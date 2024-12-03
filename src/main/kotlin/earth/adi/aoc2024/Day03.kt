package earth.adi.aoc2024

class Day03 {

  companion object {
    const val INPUT_DAY_03 = "/day_03.txt"

    private const val DISABLE_TOKEN = "don't()"
    private const val ENABLE_TOKEN = "do()"

    fun part1(inputResourceName: String): Int {
      val input = input(inputResourceName)
      val result = sumMul(findAllMul(input))
      println("Day03 part1 = $result")
      return result
    }

    fun part2(inputResourceName: String): Int {
      val input = input(inputResourceName)
      val result = sumMul(findAllMul(excludeDisabled(input)))
      println("Day03 part2 = $result")
      return result
    }

    fun findAllMul(text: String): List<Pair<Int, Int>> {
      return Regex("""mul\((\d+),(\d+)\)""", RegexOption.MULTILINE).findAll(text).toList().map {
        Pair(it.groupValues[1].toInt(), it.groupValues[2].toInt())
      }
    }

    fun sumMul(list: List<Pair<Int, Int>>): Int {
      return list.sumOf { it.first * it.second }
    }

    fun excludeDisabled(text: String): String {
      val out = StringBuilder()
      var enabled = true
      text.forEachIndexed { index, c ->
        if (enabled) {
          out.append(c)
        }
        if (index < text.length - DISABLE_TOKEN.length &&
            text.substring(index, index + DISABLE_TOKEN.length) == DISABLE_TOKEN) {
          enabled = false
        }
        if (index < text.length - ENABLE_TOKEN.length &&
            text.substring(index, index + ENABLE_TOKEN.length) == ENABLE_TOKEN) {
          enabled = true
        }
      }
      return out.toString()
    }

    fun input(resourceName: String): String {
      val resource =
          Any::class::class.java.getResourceAsStream(resourceName)
              ?: throw IllegalArgumentException("Resource $resourceName not found")
      return resource.bufferedReader().readText()
    }
  }
}
