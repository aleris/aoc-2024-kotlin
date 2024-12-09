package earth.adi.aoc2024

class Day09 {
  data class Block(var id: Int, var length: Long, var isEmpty: Boolean)

  data class DiskMap(val blocks: MutableList<Block>) {
    fun print() {
      blocks.forEach { block ->
        if (block.isEmpty) {
          print(".".repeat(block.length.toInt()))
        } else {
          print("${block.id}".repeat(block.length.toInt()))
        }
      }
      println()
    }

    fun defrag(printSteps: Boolean) {
      if (printSteps) this.print()
      var emptyIndex = 0
      var fullIndex = blocks.size - 1

      while (true) {
        emptyIndex = findNextEmpty(emptyIndex, fullIndex)
        if (emptyIndex == -1) {
          break
        }
        fullIndex = findPreviousFull(fullIndex, emptyIndex)
        if (fullIndex == -1) {
          break
        }
        val emptyBlock = blocks[emptyIndex]
        val fullBlock = blocks[fullIndex]

        if (fullBlock.length == emptyBlock.length) {
          // equal blocks, swap them
          blocks[fullIndex] = emptyBlock
          blocks[emptyIndex] = fullBlock
          if (printSteps) this.print()
        } else if (fullBlock.length < emptyBlock.length) {
          // full block is smaller, fits in empty
          // move full block
          blocks.add(emptyIndex, fullBlock)
          // shrink empty block
          emptyBlock.length -= fullBlock.length
          // replace full block with empty
          blocks[fullIndex + 1] = Block(-fullBlock.id * 2 - 1, fullBlock.length, true)
          if (printSteps) this.print()
        } else {
          // full block bigger than empty
          // move the entire empty to back
          blocks.add(fullIndex + 1, emptyBlock)
          blocks.removeAt(emptyIndex)
          // split full block in two
          blocks.add(emptyIndex, Block(fullBlock.id, emptyBlock.length, false))
          fullBlock.length -= emptyBlock.length
          if (printSteps) this.print()
        }
      }
    }

    fun compact(printSteps: Boolean) {
      if (printSteps) this.print()
      var emptyIndex = 0
      var fullIndex = blocks.size - 1

      while (true) {
        fullIndex = findPreviousFull(fullIndex, 0)
        if (fullIndex == -1) {
          break
        }
        val fullBlock = blocks[fullIndex]
        emptyIndex = findNextEmpty(0, fullIndex, fullBlock.length)
        if (emptyIndex == -1) {
          fullIndex--
          continue
        }
        val emptyBlock = blocks[emptyIndex]

        if (fullBlock.length == emptyBlock.length) {
          // equal blocks, swap them
          blocks[fullIndex] = emptyBlock
          blocks[emptyIndex] = fullBlock
          if (printSteps) this.print()
        } else {
          // full block is smaller, fits in empty
          // move full block
          blocks.add(emptyIndex, fullBlock)
          // shrink empty block
          emptyBlock.length -= fullBlock.length
          // replace full block with empty
          blocks[fullIndex + 1] = Block(-fullBlock.id * 2 - 1, fullBlock.length, true)
          if (printSteps) this.print()
        }
      }
    }

    fun findNextEmpty(fromIncluding: Int, untilExcluding: Int): Int {
      for (i in fromIncluding until untilExcluding) {
        if (blocks[i].isEmpty) {
          return i
        }
      }
      return -1
    }

    fun findNextEmpty(fromIncluding: Int, untilExcluding: Int, minLength: Long): Int {
      for (i in fromIncluding until untilExcluding) {
        val block = blocks[i]
        if (block.isEmpty && minLength <= block.length) {
          return i
        }
      }
      return -1
    }

    fun findPreviousFull(fromIncluding: Int, downToExcluding: Int): Int {
      for (i in fromIncluding downTo downToExcluding + 1) {
        if (!blocks[i].isEmpty) {
          return i
        }
      }
      return -1
    }

    fun checksum(): Long {
      var c = 0L
      var bi = 0
      for (i in 0 until blocks.size) {
        val b = blocks[i]
        if (!b.isEmpty) {
          for (j in 0 until b.length) {
            if (b.id < 0) {
              println(b)
            }
            c += bi * b.id
            bi++
          }
        } else {
          bi += b.length.toInt()
        }
      }
      return c
    }

    companion object {
      fun from(text: String): DiskMap {
        return DiskMap(
            text
                .mapIndexed { i, c ->
                  val isEmpty = i % 2 == 1
                  val id = (if (isEmpty) -i / 2 - 1 else i / 2)
                  val length = c.digitToInt().toLong()
                  Block(id, length, isEmpty)
                }
                .filter { it.length != 0L }
                .toMutableList())
      }
    }
  }

  companion object {
    const val INPUT_DAY_09 = "/day_09.txt"

    fun part1(inputResourceName: String): Long {
      val diskMap = input(inputResourceName)
      diskMap.defrag(false)
      //      diskMap.print()
      val result = diskMap.checksum()
      println("Day09 part1 = $result")
      return result
    }

    fun part2(inputResourceName: String): Long {
      val diskMap = input(inputResourceName)
      diskMap.compact(false)
      val result = diskMap.checksum()
      println("Day09 part2 = $result")
      return result
    }

    fun input(resourceName: String): DiskMap {
      val resource =
          Any::class::class.java.getResourceAsStream(resourceName)
              ?: throw IllegalArgumentException("Resource $resourceName not found")
      val text = resource.bufferedReader().readText().trim()
      return DiskMap.from(text)
    }
  }
}
