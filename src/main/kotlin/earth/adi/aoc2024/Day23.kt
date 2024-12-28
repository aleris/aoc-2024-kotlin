package earth.adi.aoc2024

class Day23 {

  data class LinkRef(val n1: String, val n2: String)

  class Graph(val adjacencyMap: Map<String, Set<String>>) {
    val nodes: Set<String>
      get() = adjacencyMap.keys

    val size: Int
      get() = adjacencyMap.size

    fun hasEdge(n1: String, n2: String): Boolean = adjacencyMap[n1]?.contains(n2) == true

    fun findTrianglesT(): Set<Set<String>> {
      val triangles = mutableSetOf<Set<String>>()
      val edges = adjacencyMap.flatMap { (node, neighbors) -> neighbors.map { node to it } }
      for (edge in edges) {
        for (node in nodes) {
          if (hasEdge(edge.first, node) && hasEdge(edge.second, node)) {
            val triangle = setOf(edge.first, edge.second, node)
            if (triangle.any { it.startsWith("t") }) {
              triangles.add(triangle)
            }
          }
        }
      }

      return triangles
    }

    fun cliqueOf(node: String): Set<String> {
      val clique = mutableSetOf(node)
      for (n in nodes.minus(node)) {
        if (clique.all { hasEdge(it, n) }) {
          clique.add(n)
        }
      }
      return clique
    }

    fun findLargestClique(): Set<String> {
      return nodes.map { cliqueOf(it) }.maxBy { it.size }
    }

    companion object {
      fun from(links: List<LinkRef>): Graph {
        val adjacencyMap = mutableMapOf<String, MutableSet<String>>()
        links.forEach { link ->
          adjacencyMap.getOrPut(link.n1) { mutableSetOf() }.add(link.n2)
          adjacencyMap.getOrPut(link.n2) { mutableSetOf() }.add(link.n1)
        }
        return Graph(adjacencyMap)
      }
    }
  }

  companion object {
    const val INPUT_DAY_23 = "/day_23.txt"

    fun part1(inputResourceName: String): Long {
      val linkRefs = input(inputResourceName)
      val graph = Graph.from(linkRefs)
      val result = graph.findTrianglesT().size.toLong()
      println("Day23 part1 = $result")
      return result
    }

    fun part2(inputResourceName: String): String {
      val linkRefs = input(inputResourceName)
      val result = Graph.from(linkRefs).findLargestClique().sorted().joinToString(",")
      println("Day23 part2 = $result")
      return result
    }

    fun input(resourceName: String): List<LinkRef> {
      val resource =
          Any::class::class.java.getResourceAsStream(resourceName)
              ?: throw IllegalArgumentException("Resource $resourceName not found")
      return resource
          .bufferedReader()
          .readLines()
          .filter { it.isNotBlank() }
          .map { it.split("-").let { LinkRef(it[0], it[1]) } }
    }
  }
}
