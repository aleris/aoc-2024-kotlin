package earth.adi.aoc2024

import earth.adi.aoc2024.Day22.Change
import earth.adi.aoc2024.Day22.Changes
import earth.adi.aoc2024.Day22.Companion.INPUT_DAY_22
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day22KtTest {
  companion object {
    private const val INPUT_DAY_22_TEST = "/day_22_test.txt"
    private const val INPUT_DAY_22_TEST_B = "/day_22_test_b.txt"
  }

  @Test
  fun part1() {
    Day22.part1(INPUT_DAY_22)
  }

  @Test
  fun part2() {
    Day22.part2(INPUT_DAY_22)
  }

  @Test
  fun part1_secret_mix() {
    val secret = Day22.Secret(42)
    secret.mix(15)
    assertThat(secret.value).isEqualTo(37L)
  }

  @Test
  fun part1_secret_prune() {
    val secret = Day22.Secret(100000000)
    secret.prune()
    assertThat(secret.value).isEqualTo(16113920L)
  }

  @Test
  fun part1_secret_next() {
    val secret = Day22.Secret(123)
    assertThat(secret.next()).isEqualTo(15887950L)
    assertThat(secret.next()).isEqualTo(16495136L)
    assertThat(secret.next()).isEqualTo(527345L)
    assertThat(secret.next()).isEqualTo(704524L)
    assertThat(secret.next()).isEqualTo(1553684L)
    assertThat(secret.next()).isEqualTo(12683156L)
    assertThat(secret.next()).isEqualTo(11100544L)
    assertThat(secret.next()).isEqualTo(12249484L)
    assertThat(secret.next()).isEqualTo(7753432L)
    assertThat(secret.next()).isEqualTo(5908254L)
  }

  @Test
  fun part1_test() {
    assertThat(Day22.part1(INPUT_DAY_22_TEST)).isEqualTo(37327623L)
  }

  @Test
  fun part2_changes() {
    assertThat(Day22.Secret(123).changes(10).changes)
        .containsExactly(
            Change(-3, 0),
            Change(6, 6),
            Change(-1, 5),
            Change(-1, 4),
            Change(0, 4),
            Change(2, 6),
            Change(-2, 4),
            Change(0, 4),
            Change(-2, 2))
  }

  @Test
  fun part2_findSeq() {
    val changes = Day22.Secret(123).changes(10)
    assertThat(changes.find(Changes.keyOf(listOf(-1, -1, 0, 2)))).isEqualTo(6)
  }

  @Test
  fun part2_buy() {
    listOf(1L, 2L, 3L, 2024L).forEach {
      println(Day22.Secret(it).changes(2000).buy(Changes.keyOf(listOf(-2, 1, -1, 3))))
    }
    assertThat(
            listOf(1L, 2L, 3L, 2024L).sumOf {
              Day22.Secret(it).changes(2000).buy(Changes.keyOf(listOf(-2, 1, -1, 3)))
            })
        .isEqualTo(23)
  }

  @Test
  fun part2_test() {
    println(Changes.keyOf(listOf(-2, 1, -1, 3)))
    assertThat(Day22.part2(INPUT_DAY_22_TEST_B)).isEqualTo(23)
  }
}
