package com.ridwanstandingby.verve.tools

import com.ridwanstandingby.verve.Api

@Api
fun Long.nsToS(): Double = toDouble() / 1_000_000_000.0

@Api
fun Double.sToNs(): Long = (this * 1_000_000_000.0).toLong()

@Api
fun Double.sToMs(): Long = (this * 1_000).toLong()

@Api
fun Double.extractNsRemainderFromSToMs(): Int = (this.sToNs() % 1_000_000).toInt()
