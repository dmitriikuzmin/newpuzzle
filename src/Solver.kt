package core

fun solve(): List<String> {
    val init = Puzzle(4).shuffle()

    val queue = mutableListOf(Node(null, init))

    val set = mutableSetOf(init.toString())

    while (set.size < 50000) {

        queue.sortBy { it.score }

        val node = queue.first()

        queue.remove(node)

        set.add(node.toString())

        if (node.solved()) {
            return node.path()
        }

        val actions = node.actions()

        actions.forEach {
            val newPuzzle = node.puzzle.move(it)

            val child = Node(node, newPuzzle)

            if (!set.contains(child.toString())) {
                queue.add(child)
               // set.add(child.toString())
            }
        }
       // queue.remove(node)
    }

    return listOf(init.toString(),init.manhattan().toString(),queue.first().toString(), queue.first().puzzle.manhattan().toString())
}


fun main() {

    val smth = solve()

    println(smth.joinToString(separator = "\n"))

    println(smth.size)
}