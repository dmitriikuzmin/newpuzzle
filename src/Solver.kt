package core


fun solve(): List<String> {
    val init = Puzzle(3).shuffle()

    val queue = mutableListOf(Node(null, init))

    val set = mutableSetOf<String>(init.toString())

    while (queue.isNotEmpty()) {

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

    return listOf()
}


fun main() {


//    val puzzle = Puzzle(3)
//
//    println("Паззл до шафлла\n$puzzle")
//
//    puzzle.shuffle()
//
//    println(puzzle)

    val smth = solve()

    println(smth.joinToString(separator = "\n"))

    println(smth.size)



//    val puzzle = Puzzle(3)
//
//    println("Паззл до шафлла\n$puzzle")
//
//    puzzle.shuffle()
//
//    println(puzzle)
//
//    val cell = puzzle.find(10)
//
//    println(cell)
//
//    val truval = puzzle.get(cell)
//
//    println(truval)
//
//    println(puzzle.manhattan())

//
//    val smth = puzzle.move(Cell(0,-1))
//
//    println(smth)
//
//    println("Паззл после шафлла\n$puzzle")
//
//    val copy = puzzle.copy()
//
//    println("Copy\n$copy")
//
//    val moved = puzzle.move(Cell(0,1))
//
//    println("Новый паззл после движения\n$moved")
//
//    println("Старый паззл\n$puzzle")
//
//    val node = Node(null, puzzle)
//
//    println("Внутренность ноде\n$node")
//
//    val another = Node(node, moved)
//
//    println("Внутренность второго node\n$another")
//
//    val path = another.path()
//
//    val str = path.joinToString(separator = "\n")
//
//    println("Путь\n$str")
}