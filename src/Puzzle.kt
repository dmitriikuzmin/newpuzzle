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
                this.numbers[i][j] = random[size * i + j]
            }
        }

        return this
    }

    private fun solvable(list: List<Int>): Boolean {
        var counter = 0

        val pos = when (list.indexOf(0)) {
            in 1..3, in 8..11 -> true
            else -> false
        }

        val newList = list.toMutableList()
        newList.remove(0)

        newList.forEach {
            counter += newList.subList(newList.indexOf(it), newList.size).count { number -> number < it }
        }

        return (counter % 2 == 0 && !pos) || (counter % 2 == 1 && pos)
    }


    fun get(cell: Cell): Int {
        return numbers[cell.x][cell.y]
    }

    private fun find(value: Int): Cell {
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

    fun move(to: Cell): Puzzle {
        val newPuzzle = Puzzle(size)

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

    private val up = Cell(-1, 0)
    private val down = Cell(1, 0)
    private val right = Cell(0, 1)
    private val left = Cell(0, -1)

    fun actions(): List<Cell> {
        val zero = find(0)
        val row = zero.x
        val column = zero.y
        val actions = mutableListOf<Cell>()

        if (column != 0) actions.add(left)
        if (column != 3) actions.add(right)
        if (row != 0) actions.add(up)
        if (row != 3) actions.add(down)

        return actions
    }


    fun h(): Int {
        var counter = 0

        for (i in 0..15) {
            val now = find(i)

            val needed = when {
                i == 0 -> Cell(3,3)
                i < 5 -> Cell(0,i-1)
                i < 9 -> Cell(1,i-5)
                i < 13 -> Cell(2,i-9)
                else -> Cell(3,i-13)
            }

            counter += abs(now.x - needed.x) + abs(now.y - needed.y)
        }

        return counter
    }

    fun win(): Boolean = h() == 0

    override fun toString(): String {
        var str = ""
        for (i in numbers.indices) {
            val row = numbers[i].joinToString(separator = " ")
            str += "$row\n"
        }
        return str
    }

//    fun flat(): MutableList<Int> {
//        val list = mutableListOf<Int>()
//
//        for (i in numbers.indices) {
//            for (j in numbers.indices) {
//                list.add(numbers[i][j])
//            }
//        }
//
//        return list
//    }
}