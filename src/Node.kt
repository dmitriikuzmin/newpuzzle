package core

class Node constructor(private val parent: Node? = null, val puzzle: Puzzle, accuracy: Double) {

    private val depth: Int = if (parent != null) parent.depth + 1 else 0

    val score: Double = this.depth * accuracy + puzzle.h()

    fun solved(): Boolean {
        return puzzle.win()
    }

    fun actions(): List<Cell> {
        return puzzle.actions()
    }

    override fun toString(): String {
        return puzzle.toString()
    }

    fun h(): Int {
        return puzzle.h()
    }

    fun path(): List<String> {
        val list = mutableListOf<String>()
        var node = this

        while (node.parent != null) {
            list.add(node.toString())
            node = node.parent!!
        }

        list.add(node.toString())

        list.reverse()
        return list
    }
}