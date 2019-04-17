package com.example.smartalarm

fun Int.addBit(bit: Int) = this or bit

fun Int.removeBit(bit: Int) = addBit(bit) - bit