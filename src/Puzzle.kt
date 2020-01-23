package core

import kotlin.math.abs

data class Puzzle constructor(private val size: Int) {

    private val numbers = Array(size) { IntArray(size) }

    fun shuffle(): Puzzle {

        var random = when (size) {
            3 -> (0..8).shuffled().toList()
            else -> (0..15).shuffled().toList()

        }

        while (!solvable(random)) {
            random = random.shuffled()
        }

        for (i in 0 until size) {
            for (j in 0 until size) {
                this.numbers[i][j] = random[size * i + j] // 4i + j for 4
            }
        }

        return this
    }


    fun get(cell: Cell): Int {
        return numbers[cell.x][cell.y]
    }


    fun move(to: Cell): Puzzle {

        /**
         * Тут захардкожено
         */
        val newPuzzle = Puzzle(4)

        for (i in numbers.indices) {
            for (j in numbers.indices) {
                newPuzzle.numbers[i][j] = this.numbers[i][j]
            }
        }

        val zero = newPuzzle.find(0)

        val x = zero.x + to.x
        val y = zero.y + to.y

        val value = newPuzzle.numbers[x][y]

        newPuzzle.numbers[zero.x][zero.y] = value
        newPuzzle.numbers[x][y] = 0

        return newPuzzle
    }


    fun find(value: Int): Cell {
        var one = 0
        var two = 0

        for (i in numbers.indices) {
            for (j in numbers.indices) {
                if (numbers[i][j] == value) {
                    one = i
                    two = j
                    break
                }
            }
        }

        return Cell(one, two)
    }

    private val up = Cell(-1, 0)
    private val down = Cell(1, 0)
    private val right = Cell(0, 1)
    private val left = Cell(-0, -1)


//    private val up = Cell(0, -1)
//    private val down = Cell(0, 1)
//    private val right = Cell(1, 0)
//    private val left = Cell(-1, 0)


    fun actions(): List<Cell> {
        val zero = find(0)
        val column = zero.x
        val row = zero.y
        val size = size - 1
        val actions: List<Cell>


        actions = when {
            row == 0 && column == 0 -> listOf(down, right)
            row == size && column == 0 -> listOf(down, left)
            row == size && column == size -> listOf(up, left)
            row == 0 && column == size -> listOf(up, right)
            (row == 1 || row == 2) && column == 0 -> listOf(right, left, down)
            (row == 1 || row == 2) && column == size -> listOf(right, left, up)
            row == 0 && (column == 1 || column == 2) -> listOf(up, down, right)
            row == size && (column == 1 || column == 2) -> listOf(up, down, left)
            else -> listOf(up, down, left, right)
        }
        return actions
    }


    private val correct = mapOf(
        0 to Cell(3, 3),
        1 to Cell(0, 0),
        2 to Cell(0, 1),
        3 to Cell(0, 2),
        4 to Cell(0, 3),
        5 to Cell(1, 0),
        6 to Cell(1, 1),
        7 to Cell(1, 2),
        8 to Cell(1, 3),
        9 to Cell(2, 0),
        10 to Cell(2, 1),
        11 to Cell(2, 2),
        12 to Cell(2, 3),
        13 to Cell(3, 0),
        14 to Cell(3, 1),
        15 to Cell(3, 2)
    )

//    private val correct = mapOf(
//        0 to Cell(2, 2),
//        1 to Cell(0, 0),
//        2 to Cell(0, 1),
//        3 to Cell(0, 2),
//        4 to Cell(1, 0),
//        5 to Cell(1, 1),
//        6 to Cell(1, 2),
//        7 to Cell(2, 0),
//        8 to Cell(2, 1)
//    )


    // Можно использовать manhattan для нахождения победы

    fun manhattan(): Int {
        var counter = 0

        for (i in 0..15) { // до 15 для 4 паззла
            val pos = find(i)
            val needed = correct[i]

            counter += abs(pos.x - needed!!.x) + abs(pos.y - needed.y)
        }

        return counter
    }


    override fun toString(): String {
        var str = ""
        for (i in numbers.indices) {
            val smth = numbers[i].joinToString(separator = " ")
            str += "$smth\n"
        }
        return str
    }


    fun win(): Boolean = manhattan() == 0


    fun solvable(list: List<Int>): Boolean {
        var counter = 0

        val pos = when (list.indexOf(0)) {
            in 1..3, in 8..11 -> true
            else -> false
        }

        val newList = list.toMutableList()
        newList.remove(0)

        newList.forEach {
            val check = newList.subList(newList.indexOf(it), newList.size)
            val smth = check.count { number -> number < it }
            counter += smth
        }

        return (counter % 2 == 0 && !pos) || (counter % 2 == 1 && pos)
    }
}