package earth.adi.aoc2024

class Day03 {

  companion object {
    const val INPUT_DAY_03 = "/day_03.txt"

    private const val DISABLE_TOKEN = "don't()"
    private const val ENABLE_TOKEN = "do()"
    private const val MUL_START_TOKEN = "mul("
    private const val MUL_SEPARATOR_TOKEN = ','
    private const val MUL_END_TOKEN = ')'

    fun part1(inputResourceName: String): Int {
      val input = input(inputResourceName)
      val result = sumMul(findAllMulNoRegex(input, false))
      println("Day03 part1 = $result")
      return result
    }

    fun part2(inputResourceName: String): Int {
      val input = input(inputResourceName)
      val result = sumMul(findAllMulNoRegex(input, true))
      println("Day03 part2 = $result")
      return result
    }

    fun findAllMulNoRegex(text: String, allowDisable: Boolean): List<Pair<Int, Int>> {
      val result = mutableListOf<Pair<Int, Int>>()
      var enabled = true
      var i = 0
      while (i < text.length) {
        if (i >= ENABLE_TOKEN.length &&
            text.substring(i - ENABLE_TOKEN.length, i) == ENABLE_TOKEN) {
          enabled = true
        }
        if (!enabled) {
          i++
          continue
        }
        if (allowDisable &&
            i >= DISABLE_TOKEN.length &&
            text.substring(i - DISABLE_TOKEN.length, i) == DISABLE_TOKEN) {
          enabled = false
        }
        if (i >= MUL_START_TOKEN.length &&
            text.substring(i - MUL_START_TOKEN.length, i) == MUL_START_TOKEN) {
          val d1i = i
          while (i < text.length && text[i].isDigit()) {
            i++
          }
          if (i == d1i) {
            continue
          }
          val d1 = text.substring(d1i, i).toInt()
          if (text[i] != MUL_SEPARATOR_TOKEN) {
            continue
          }
          i++
          val d2i = i
          while (i < text.length && text[i].isDigit()) {
            i++
          }
          if (i == d2i) {
            continue
          }
          if (text[i] != MUL_END_TOKEN) {
            continue
          }
          val d2 = text.substring(d2i, i).toInt()
          result.add(Pair(d1, d2))
        }
        i++
      }
      return result
    }

    fun sumMul(list: List<Pair<Int, Int>>): Int {
      return list.sumOf { it.first * it.second }
    }

    // Regex version
    //    fun findAllMul(text: String): List<Pair<Int, Int>> {
    //      return Regex("""mul\((\d+),(\d+)\)""", RegexOption.MULTILINE).findAll(text).toList().map
    // {
    //        Pair(it.groupValues[1].toInt(), it.groupValues[2].toInt())
    //      }
    //    }
    //
    //    fun excludeDisabled(text: String): String {
    //      val out = StringBuilder()
    //      var enabled = true
    //      text.forEachIndexed { index, c ->
    //        if (enabled) {
    //          out.append(c)
    //        }
    //        if (index < text.length - DISABLE_TOKEN.length &&
    //            text.substring(index, index + DISABLE_TOKEN.length) == DISABLE_TOKEN) {
    //          enabled = false
    //        }
    //        if (index < text.length - ENABLE_TOKEN.length &&
    //            text.substring(index, index + ENABLE_TOKEN.length) == ENABLE_TOKEN) {
    //          enabled = true
    //        }
    //      }
    //      return out.toString()
    //    }

    fun input(resourceName: String): String {
      val resource =
          Any::class::class.java.getResourceAsStream(resourceName)
              ?: throw IllegalArgumentException("Resource $resourceName not found")
      return resource.bufferedReader().readText()
    }
  }
}
