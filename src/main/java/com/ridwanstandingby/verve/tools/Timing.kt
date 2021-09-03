package com.ridwanstandingby.verve.tools

fun Long.nsToS(): Double = toDouble() / 1_000_000_000.0
fun Double.sToNs(): Long = (this * 1_000_000_000.0).toLong()
fun Double.sToMs(): Long = (this * 1_000).toLong()
fun Double.extractNsRemainderFromSToMs(): Int = (this.sToNs() % 1_000_000).toInt()
