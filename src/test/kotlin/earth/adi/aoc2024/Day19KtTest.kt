package earth.adi.aoc2024

import earth.adi.aoc2024.Day19.Companion.INPUT_DAY_19
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day19KtTest {
  companion object {
    private const val INPUT_DAY_19_TEST = "/day_19_test.txt"
  }

  @Test
  fun part1() {
    Day19.part1(INPUT_DAY_19)
  }

  @Test
  fun part2() {
    Day19.part2(INPUT_DAY_19)
  }

  @Test
  fun part1_isDesignPossible() {
    val onsen = Day19.input(INPUT_DAY_19_TEST)
    println(onsen)
    val designSolver = Day19.DesignSolver(onsen.towels)
    assertThat(designSolver.isDesignPossible("bwurrg")).isTrue()
  }

  @Test
  fun part1_isDesignPossible1() {
    val onsen = Day19.input(INPUT_DAY_19)
    println(onsen)
    val designSolver = Day19.DesignSolver(onsen.towels)
    println(designSolver.isDesignPossible("ggwrwwuugugwgubggggbrguwbwwbugrugwubwugbrbwbbbgb"))
  }

  @Test
  fun part1_test() {
    assertThat(Day19.part1(INPUT_DAY_19_TEST)).isEqualTo(6)
  }

  @Test
  fun part2_test() {
    assertThat(Day19.part2(INPUT_DAY_19_TEST)).isEqualTo(-1)
  }
}
