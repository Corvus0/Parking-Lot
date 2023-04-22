package parking

fun main() {
    var parkingLot: ParkingLot? = null
    while (true) {
        val input = readln().trim().split(" ")
        if (parkingLot == null && input[0] != "create" && input[0] != "exit") {
            println("Sorry, a parking lot has not been created.")
            continue
        }
        when (input[0]) {
            "create" -> {
                val size = input[1].toInt()
                parkingLot = ParkingLot(size)
            }
            "park" -> {
                val regNum = input[1]
                val color = input[2]
                parkingLot?.park(regNum, color)
            }
            "leave" -> {
                val spot = input[1].toInt()
                parkingLot?.leave(spot)
            }
            "status" -> parkingLot?.status()
            "reg_by_color" -> {
                val color = input[1].uppercase()
                parkingLot?.regByColor(color)
            }
            "spot_by_color" -> {
                val color = input[1].uppercase()
                parkingLot?.spotByColor(color)
            }
            "spot_by_reg" -> {
                val reg = input[1]
                parkingLot?.spotByReg(reg)
            }
            "exit" -> break
        }
    }
}

private data class Car(val regNum: String, val color: String)

class ParkingLot(private val size: Int = 20) {
    init {
        println("Created a parking lot with $size spots.")
    }

    private val spots = Array<Car?>(size) { null }
    private var filled = 0

    private fun getLowestSpot(): Int {
        for (i in spots.indices) {
            if (spots[i] == null) return i
        }
        return -1
    }

    fun park(regNum: String, color: String) {
        if (filled == size) {
            println("Sorry, the parking lot is full.")
            return
        }
        val car = Car(regNum, color)
        val spot = getLowestSpot()
        spots[spot] = car
        filled++
        println("$color car parked in spot ${spot + 1}.")
    }

    fun leave(spot: Int) {
        if (spots[spot - 1] == null) {
            println("There is no car in spot $spot.")
            return
        }
        spots[spot - 1] = null
        filled--
        println("Spot $spot is free.")
    }


    fun status() {
        if (filled == 0) {
            println("Parking lot is empty.")
            return
        }
        for (i in spots.indices) {
            if(spots[i] != null) println("${i + 1} ${spots[i]?.regNum} ${spots[i]?.color}")
        }
    }

    fun regByColor(color: String) {
        val regs = mutableListOf<String>()
        for (spot in spots) {
            if (spot?.color?.uppercase() == color) regs.add(spot.regNum)
        }
        if (regs.size == 0) println("No cars with color $color were found.")
        else println(regs.joinToString(", "))
    }

    fun spotByColor(color: String) {
        val regs = mutableListOf<Int>()
        for (i in spots.indices) {
            if (spots[i]?.color?.uppercase() == color) regs.add(i + 1)
        }
        if (regs.size == 0) println("No cars with color $color were found.")
        else println(regs.joinToString(", "))
    }

    fun spotByReg(reg: String) {
        for (i in spots.indices) {
            if (spots[i]?.regNum == reg) {
                println(i + 1)
                return
            }
        }
        println("No cars with registration number $reg were found.")
    }

}
