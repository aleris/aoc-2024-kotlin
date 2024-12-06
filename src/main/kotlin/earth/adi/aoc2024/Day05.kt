package earth.adi.aoc2024

class Day05 {

  data class ManualUpdates(val updates: List<List<Int>>, val orderingRules: OrderingRules)

  data class OrderingRules(
      val pagesAfter: Map<Int, List<Int>>,
      val pagesBefore: Map<Int, List<Int>>,
  )

  companion object {
    const val INPUT_DAY_05 = "/day_05.txt"

    fun part1(inputResourceName: String): Int {
      val input = input(inputResourceName)
      val result = allOrderedUpdates(input).sumOf { middle(it) }
      println("Day05 part1 = $result")
      return result
    }

    fun part2(inputResourceName: String): Int {
      val input = input(inputResourceName)
      val result =
          allUpdatesNotOrdered(input).sumOf {
            val reordered = reorderIfNeeded(it, input.orderingRules)
            middle(reordered)
          }
      println("Day05 part2 = $result")
      return result
    }

    fun allOrderedUpdates(manualUpdates: ManualUpdates): List<List<Int>> {
      return manualUpdates.updates.filter { isOrdered(it, manualUpdates.orderingRules) }
    }

    fun isOrdered(
        update: List<Int>,
        orderingRules: OrderingRules,
    ): Boolean {
      update.subList(0, update.size).forEachIndexed { index, page ->
        val previousUpdatePages = update.subList(0, index).toSet()
        if (previousUpdatePages.isNotEmpty()) {
          val pagesBefore = orderingRules.pagesBefore[page] ?: emptyList()
          if (previousUpdatePages.any { it !in pagesBefore }) {
            return false
          }
        }

        val followingUpdatePages = update.subList(index + 1, update.size).toSet()
        if (followingUpdatePages.isNotEmpty()) {
          val pagesAfter = orderingRules.pagesAfter[page] ?: emptyList()
          if (followingUpdatePages.any { it !in pagesAfter }) {
            return false
          }
        }
      }
      return true
    }

    fun middle(update: List<Int>): Int {
      return update[update.size / 2]
    }

    fun allUpdatesNotOrdered(manualUpdates: ManualUpdates): List<List<Int>> {
      return manualUpdates.updates.filter { !isOrdered(it, manualUpdates.orderingRules) }
    }

    fun reorderIfNeeded(update: List<Int>, orderingRules: OrderingRules): List<Int> {
      update.subList(0, update.size).forEachIndexed { index, page ->
        val previousUpdatePages = update.subList(0, index).toSet()
        if (previousUpdatePages.isNotEmpty()) {
          val pagesBefore = orderingRules.pagesBefore[page] ?: emptyList()
          if (previousUpdatePages.any { it !in pagesBefore }) {
            val mutableUpdate = update.toMutableList()
            swap(mutableUpdate, previousUpdatePages.first { it !in pagesBefore }, index, page)
            return reorderIfNeeded(mutableUpdate, orderingRules)
          }
        }

        val followingUpdatePages = update.subList(index + 1, update.size).toSet()
        if (followingUpdatePages.isNotEmpty()) {
          val pagesAfter = orderingRules.pagesAfter[page] ?: emptyList()
          if (followingUpdatePages.any { it !in pagesAfter }) {
            val mutableUpdate = update.toMutableList()
            swap(mutableUpdate, followingUpdatePages.first { it !in pagesAfter }, index, page)
            return reorderIfNeeded(mutableUpdate, orderingRules)
          }
        }
      }
      return update
    }

    private fun swap(
        update: MutableList<Int>,
        pageToSwap: Int,
        index: Int,
        withPage: Int,
    ) {
      val indexToSwap = update.indexOf(pageToSwap)
      update[index] = pageToSwap
      update[indexToSwap] = withPage
    }

    fun input(resourceName: String): ManualUpdates {
      val resource =
          Any::class::class.java.getResourceAsStream(resourceName)
              ?: throw IllegalArgumentException("Resource $resourceName not found")
      val lines = resource.bufferedReader().readLines()

      val pagesAfterRules =
          lines
              .takeWhile { it.isNotBlank() }
              .map { it.split("|").map { it.toInt() } }
              .map { it[0] to it[1] }
              .groupBy({ it.first }, { it.second })

      val pagesBeforeRules =
          pagesAfterRules
              .flatMap { p -> p.value.map { it to p.key } }
              .groupBy({ it.first }, { it.second })

      val updates =
          lines
              .dropWhile { it.isNotBlank() }
              .filter { it.isNotBlank() }
              .map { it.split(",").map { it.toInt() } }

      return ManualUpdates(updates, OrderingRules(pagesAfterRules, pagesBeforeRules))
    }
  }
}
