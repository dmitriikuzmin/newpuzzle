package core

data class Puzzle constructor(private val size: Int) {

    private val numbers = Array(size) { IntArray(size) }

    fun shuffle(): Puzzle {
        //val list = mutableListOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
        //val list = mutableListOf(1,2,3,4,5,6,7,8,9,10,11,12,13,14,0,15)

        // for 15 puzzle
        val random = (0..15).shuffled().toList()

        for (i in 0 until size) {
            for (j in 0 until size) {
                this.numbers[i][j] = random[4 * i + j]
            }
        }

        return this


        // for 8 puzzle
//        val random = (0..8).shuffled().toList()
//
//        for (i in 0 until size) {
//            for (j in 0 until size) {
//                this.numbers[i][j] = random[2 * i + j]
//            }
//        }
//
//        return this
    }

    fun move(to: Cell): Puzzle {
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
                    one = j
                    two = i
                    break
                }
            }
        }

        return Cell(one, two)
    }

    private val up = Cell(0, -1)
    private val down = Cell(0, 1)
    private val right = Cell(1, 0)
    private val left = Cell(-1, 0)


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
        2 to Cell(1, 0),
        3 to Cell(2, 0),
        4 to Cell(3, 0),
        5 to Cell(0, 1),
        6 to Cell(1, 1),
        7 to Cell(2, 1),
        8 to Cell(3, 1),
        9 to Cell(0, 2),
        10 to Cell(1, 2),
        11 to Cell(2, 2),
        12 to Cell(3, 2),
        13 to Cell(0, 3),
        14 to Cell(1, 3),
        15 to Cell(2, 3)
    )

    // Можно использовать manhattan для нахождения победы

    fun manhattan(): Int {
        var counter = 0

        for (i in 0 until 15) {
            val pos = find(i)
            val needed = correct[i]

            counter += if (pos != needed) 1 else 0
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


//        val list = mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0)
//
//        for (i in numbers.indices) {
//            for (j in numbers.indices) {
//                if (numbers[j][i] != list[4 * i + j]) {
//                    return false
//                }
//            }
//        }
//        return true
//    }
}