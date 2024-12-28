package earth.adi.aoc2024

class Day24 {

  enum class Operator {
    AND,
    OR,
    XOR,
    INPUT,
  }

  data class Circuit(val gates: Map<String, Gate>) {

    val zGates: List<Gate>
      get() = gates.keys.filter { it.startsWith("z") }.map { gates[it]!! }

    fun evaluate(gate: Gate) {
      gate.evaluate(this)
    }

    fun isComplete(): Boolean {
      return zGates.all { it.value != null }
    }

    fun evaluate() {
      gates.values.forEach { evaluate(it) }
    }

    fun evaluateUntilComplete(): Long {
      while (!isComplete()) {
        evaluate()
        //        print()
      }
      return zGates.sortedByDescending { it.out }.map { it.value!! }.joinToString("").toLong(2)
    }

    fun print() {
      println(
          gates.values.joinToString("\n") {
            "${it.inputId1}=${gates[it.inputId1]?.out ?: ""} ${it.inputId2}=${gates[it.inputId2]?.out ?: ""} -> ${it.out}=${gates[it.out]!!.value}"
          })
    }

    fun findSwappedGates(): String {
      val lastZ = "z45"
      val nonXorOut = zGates.filter { it.out != lastZ && it.operator != Operator.XOR }

      return ""
    }

    companion object {
      fun from(text: String): Circuit {
        val lines = text.lines()
        val inputGates =
            lines
                .takeWhile { it.isNotBlank() }
                .map { it.split(": ").let { (out, value) -> Gate.input(out, value.toInt()) } }
        val functionGates =
            lines.dropWhile { it.isNotBlank() }.filter { it.isNotBlank() }.map { Gate.function(it) }
        val gates = inputGates + functionGates
        return Circuit(gates.associateBy { it.out })
      }
    }
  }

  data class Gate(
      val out: String,
      val inputId1: String,
      val inputId2: String,
      val operator: Operator,
      var value: Int? = null
  ) {

    fun evaluate(circuit: Circuit) {
      if (value != null) {
        return
      }
      val input1 = circuit.gates[inputId1]!!.value
      if (input1 == null) {
        return
      }
      val input2 = circuit.gates[inputId2]!!.value
      if (input2 == null) {
        return
      }
      value =
          when (operator) {
            Operator.AND -> input1 and input2
            Operator.OR -> input1 or input2
            Operator.XOR -> input1 xor input2
            Operator.INPUT -> value!!
          }
    }

    companion object {

      fun input(out: String, value: Int): Gate {
        val gate = Gate(out, "", "", Operator.INPUT)
        gate.value = value
        return gate
      }

      fun function(text: String): Gate {
        val (input, out) = text.split(" -> ")
        return input.split(" ").let { (input1, op, input2) ->
          Gate(out, input1, input2, Operator.valueOf(op))
        }
      }
    }
  }

  companion object {
    const val INPUT_DAY_24 = "/day_24.txt"

    fun part1(inputResourceName: String): Long {
      val input = input(inputResourceName)
      val result = Circuit.from(input).evaluateUntilComplete()
      println("Day24 part1 = $result")
      return result
    }

    fun part2(inputResourceName: String): String {
      val input = input(inputResourceName)
      val result = Circuit.from(input).findSwappedGates()
      println("Day24 part2 = $result")
      return result
    }

    fun input(resourceName: String): String {
      val resource =
          Any::class::class.java.getResourceAsStream(resourceName)
              ?: throw IllegalArgumentException("Resource $resourceName not found")
      return resource.bufferedReader().readText()
    }
  }
}
