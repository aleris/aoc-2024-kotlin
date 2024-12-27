package earth.adi.aoc2024

import earth.adi.aoc2024.Day21.*
import earth.adi.aoc2024.Day21.Companion.INPUT_DAY_21
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day21KtTest {
  companion object {
    private const val INPUT_DAY_21_TEST = "/day_21_test.txt"
  }

  @Test
  fun part1() {
    Day21.part1(INPUT_DAY_21)
  }

  @Test
  fun part2() {
    Day21.part2(INPUT_DAY_21)
  }

  @Test
  fun part1_test() {
    assertThat(Day21.part1(INPUT_DAY_21_TEST)).isEqualTo(126384L)
  }

  @Test
  fun part1_moveNumericKeypad() {
    val keypad = NumericKeypad()
    // +---+---+---+
    // | 7 | 8 | 9 |
    // +---+---+---+
    // | 4 | 5 | 6 |
    // +---+---+---+
    // | 1 | 2 | 3 |
    // +---+---+---+
    //     | 0 | A |
    //     +---+---+
    assertThat(keypad.moveAlternatives('A', '4')).contains("^^<<A", "<^<^A")
    assertThat(keypad.moveAlternatives('4', '8')).contains("^>A", ">^A")
    assertThat(keypad.moveAlternatives('8', '1')).contains("vv<A", "<vvA", "v<vA")
    assertThat(keypad.moveAlternatives('1', 'A')).contains(">>vA")
    assertThat(keypad.moveAlternatives('A', '8')).contains("<^^^A", "^^^<A")
    assertThat(keypad.moveAlternatives('8', 'A')).contains(">vvvA", "vvv>A")
    assertThat(keypad.moveAlternatives('A', '3')).contains("^A")
    assertThat(keypad.moveAlternatives('3', '1')).contains("<<A")
    assertThat(keypad.moveAlternatives('1', '7')).contains("^^A")
    assertThat(keypad.moveAlternatives('7', '9')).contains(">>A")
  }

  @Test
  fun part1_moveDirectionalKeypad() {
    //     +---+---+
    //     | ^ | A |
    // +---+---+---+
    // | < | v | > |
    // +---+---+---+
    val keypad = DirectionalKeypad()
    assertThat(keypad.moveAlternatives('A', '<')).contains("v<<A", "<v<A")
    assertThat(keypad.moveAlternatives('<', '^')).contains(">^A")
    assertThat(keypad.moveAlternatives('^', '>')).contains(">vA", "v>A")
    assertThat(keypad.moveAlternatives('>', '^')).contains("<^A", "^<A")
    assertThat(keypad.moveAlternatives('^', '<')).contains("v<A")
  }

  @Test
  fun part1_typeNumericKeypad() {
    val keypad = NumericKeypad()
    println(keypad.typeWithDirectionalKeypad("029A"))
  }

  @Test
  fun part1_shortest() {
    assertThat(Chain().shortestPathLength("029A", 1)).isEqualTo("<A^A>^^AvvvA".length.toLong())

    assertThat(Chain().shortestPathLength("029A", 2))
        .isEqualTo("v<<A>>^A<A>AvA<^AA>A<vAAA>^A".length.toLong())

    assertThat(Chain().shortestPathLength("029A", 3))
        .isEqualTo(
            "<vA<AA>>^AvAA<^A>A<v<A>>^AvA^A<vA>^A<v<A>^A>AAvA^A<v<A>A>^AAAvA<^A>A".length.toLong())
  }

  @Test
  fun part1_lengths() {
    assertThat(Chain().shortestPathLength("029A", 3))
        .isEqualTo(
            "<vA<AA>>^AvAA<^A>A<v<A>>^AvA^A<vA>^A<v<A>^A>AAvA^A<v<A>A>^AAAvA<^A>A".length.toLong())
    assertThat(Chain().shortestPathLength("980A", 3))
        .isEqualTo("<v<A>>^AAAvA^A<vA<AA>>^AvAA<^A>A<v<A>A>^AAAvA<^A>A<vA>^A<A>A".length.toLong())
    assertThat(Chain().shortestPathLength("179A", 3))
        .isEqualTo(
            "<v<A>>^A<vA<A>>^AAvAA<^A>A<v<A>>^AAvA^A<vA>^AA<A>A<v<A>A>^AAAvA<^A>A".length.toLong())
    assertThat(Chain().shortestPathLength("456A", 3))
        .isEqualTo(
            "<v<A>>^AA<vA<A>>^AAvAA<^A>A<vA>^A<A>A<vA>^A<A>A<v<A>A>^AAvA<^A>A".length.toLong())
    assertThat(Chain().shortestPathLength("379A", 3))
        .isEqualTo(
            "<v<A>>^AvA^A<vA<AA>>^AAvA<^A>AAvA^A<vA>^AA<A>A<v<A>A>^AAAvA<^A>A".length.toLong())
  }
}
