package core

fun solve(accuracy: Double, limit: Int): List<String> {
    val init = Puzzle(4).shuffle()

    val queue = mutableListOf(Node(null, init, accuracy))

    val set = mutableSetOf(init.toString())

    while (set.size < limit) {
        queue.sortBy { it.score }

        val node = queue.first()

        queue.remove(node)
        set.add(node.toString())

        val actions = node.actions()

        actions.forEach {
            val child = Node(node, node.puzzle.move(it), accuracy)

            if (child.solved()) {
                return child.path()
            }

            if (!set.contains(child.toString())) {
                queue.add(child)
            }
        }
    }

    return listOf(init.toString(),
        init.h().toString(),
        queue.first().toString(),
        queue.first().h().toString())
}

fun main() {
    val solvedPuzzle = solve(0.3, 50000)

    println(solvedPuzzle.joinToString(separator = "\n"))

    println(solvedPuzzle.size)
}