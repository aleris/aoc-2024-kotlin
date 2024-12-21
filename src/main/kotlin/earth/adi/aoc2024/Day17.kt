package earth.adi.aoc2024

import java.math.BigInteger

class Day17 {
  enum class InstructionCode {
    adv,
    bxl,
    bst,
    jnz,
    bxc,
    out,
    bdv,
    cdv,
  }

  data class Instruction(val code: InstructionCode, val operand: Int) {

    companion object {
      fun at(program: List<Int>, pointer: Int): Instruction {
        val instructionNumber = program[pointer]
        val operand = program[pointer + 1]
        return Instruction(InstructionCode.entries[instructionNumber], operand)
      }
    }
  }

  data class Registers(var A: BigInteger, var B: BigInteger, var C: BigInteger) {
    companion object {
      fun from(lines: List<String>): Registers {
        return Registers(
            parseRegister(lines[0]),
            parseRegister(lines[1]),
            parseRegister(lines[2]),
        )
      }

      private fun parseRegister(line: String): BigInteger =
          BigInteger.valueOf(line.substringAfter(": ").toLong())
    }
  }

  data class Computer(
      val registers: Registers,
      val program: List<Int>,
      var pointer: Int = 0,
      val output: MutableList<Int> = mutableListOf(),
  ) {
    fun run() {
      while (pointer < program.size) {
        val instruction = Instruction.at(program, pointer)
        when (instruction.code) {
          InstructionCode.adv -> {
            registers.A = div(registers.A, combo(instruction.operand))
          }
          InstructionCode.bxl -> {
            registers.B = registers.B xor BigInteger.valueOf(instruction.operand.toLong())
          }
          InstructionCode.bst -> {
            registers.B = combo(instruction.operand).mod(BigInteger.valueOf(8))
          }
          InstructionCode.jnz -> {
            if (registers.A != BigInteger.ZERO) {
              pointer = instruction.operand
              continue
            }
          }
          InstructionCode.bxc -> {
            registers.B = registers.B xor registers.C
          }
          InstructionCode.out -> {
            output.add(combo(instruction.operand).mod(BigInteger.valueOf(8)).toInt())
          }
          InstructionCode.bdv -> {
            registers.B = div(registers.A, combo(instruction.operand))
          }
          InstructionCode.cdv -> {
            registers.C = div(registers.A, combo(instruction.operand))
          }
        }
        pointer += 2
      }
    }

    fun findYourself(): BigInteger {
      return findRecursiveAt(BigInteger.valueOf(0), 0)
    }

    fun findRecursiveAt(registerA: BigInteger, i: Int): BigInteger {
      val c = Computer(Registers(registerA, registers.B, registers.C), program)
      c.run()
      if (c.output == program) {
        return registerA
      }
      if (c.output == program.subList(program.size - i, program.size) || i == 0) {
        for (n in 0 until program.size) {
          val nextA = registerA.multiply(BigInteger.valueOf(8)) + BigInteger.valueOf(n.toLong())
          val found = findRecursiveAt(nextA, i + 1)
          if (found != BigInteger.valueOf(-1)) {
            return found
          }
        }
      }
      return BigInteger.valueOf(-1)
    }

    fun combo(operand: Int): BigInteger {
      return when (operand) {
        0,
        1,
        2,
        3, -> BigInteger.valueOf(operand.toLong())
        4 -> registers.A
        5 -> registers.B
        6 -> registers.C
        else -> throw IllegalArgumentException("combo operand $operand not implemented")
      }
    }

    fun div(numerator: BigInteger, denominator: BigInteger): BigInteger {
      return numerator.divide(BigInteger.ONE.shiftLeft(denominator.toInt()))
    }

    companion object {
      fun from(input: String): Computer {
        val lines = input.lines()
        val registers = Registers.from(lines.take(3))
        val program = lines[4].substringAfter(" ").split(",").map { it.toInt() }
        return Computer(registers, program)
      }
    }
  }

  companion object {
    const val INPUT_DAY_17 = "/day_17.txt"

    fun part1(inputResourceName: String): String {
      val computer = input(inputResourceName)
      computer.run()
      val result = computer.output.joinToString(",")
      println("Day17 part1 = $result")
      return result
    }

    fun part2(inputResourceName: String): BigInteger {
      val computer = input(inputResourceName)
      val result = computer.findYourself()
      println("Day17 part2 = $result")
      return result
    }

    fun input(resourceName: String): Computer {
      val resource =
          Any::class::class.java.getResourceAsStream(resourceName)
              ?: throw IllegalArgumentException("Resource $resourceName not found")
      return Computer.from(resource.bufferedReader().readText().trim())
    }
  }
}
