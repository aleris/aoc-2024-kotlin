package earth.adi.aoc2024

class Day07 {
  enum class Operator {
    PLUS,
    MUL,
    CONCAT,
  }

  data class UnsolvedEquation(val result: Long, val operands: List<Long>)

  data class Equation(val operands: List<Long>, val operators: List<Operator>) {
    fun result(): Long {
      var r = operands[0]
      for (i in 1 until operands.size) {
        val operator = operators[i - 1]
        val operand = operands[i]
        when (operator) {
          Operator.PLUS -> r += operand
          Operator.MUL -> r *= operand
          Operator.CONCAT -> r = "$r$operand".toLong()
        }
      }
      return r
    }
  }

  companion object {
    const val INPUT_DAY_07 = "/day_07.txt"

    fun part1(inputResourceName: String): Long {
      val input = input(inputResourceName)
      val result =
          input.filter { isSolvable(it, listOf(Operator.PLUS, Operator.MUL)) }.sumOf { it.result }
      println("Day07 part1 = $result")
      return result
    }

    fun generateOperatorCombinations(n: Int, operators: List<Operator>): Set<List<Operator>> {
      if (n == 0) {
        return setOf(listOf())
      }
      val s = generateOperatorCombinations(n - 1, operators)
      val r = mutableSetOf<List<Operator>>()
      for (o in operators) {
        for (l in s) {
          r.add(l.plus(o))
        }
      }
      return r
    }

    private fun isSolvable(eq: UnsolvedEquation, allowedOperators: List<Operator>): Boolean {
      val operatorCombinations =
          generateOperatorCombinations(eq.operands.size - 1, allowedOperators)
      return operatorCombinations.any { operators ->
        val r = Equation(eq.operands, operators).result()
        r == eq.result
      }
    }

    fun part2(inputResourceName: String): Long {
      val input = input(inputResourceName)
      val result =
          input
              .filter { isSolvable(it, listOf(Operator.PLUS, Operator.MUL, Operator.CONCAT)) }
              .sumOf { it.result }
      println("Day07 part2 = $result")
      return result
    }

    fun input(resourceName: String): List<UnsolvedEquation> {
      val resource =
          Any::class::class.java.getResourceAsStream(resourceName)
              ?: throw IllegalArgumentException("Resource $resourceName not found")
      return resource
          .bufferedReader()
          .readLines()
          .filter { it.isNotBlank() }
          .map {
            val p = it.split(":")
            UnsolvedEquation(p[0].toLong(), p[1].trim().split(" ").map { it.toLong() })
          }
    }
  }
}
