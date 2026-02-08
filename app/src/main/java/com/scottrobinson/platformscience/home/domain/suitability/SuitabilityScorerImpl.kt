package com.scottrobinson.platformscience.home.domain.suitability

import javax.inject.Inject
import kotlin.math.abs

class SuitabilityScorerImpl @Inject constructor() : SuitabilityScorer {

    override fun score(driverName: String, shipmentDestination: String): Double {
        val sLen = streetNameLen(shipmentDestination)
        val dLen = nameLen(driverName)

        val base = if (sLen % 2 == 0) vowelCount(driverName) * 1.5 else consonantCount(driverName) * 1.0
        return if (gcd(sLen, dLen) > 1) base * 1.5 else base
    }

    private fun streetNameLen(address: String): Int {
        // Remove leading street number token if present; count letters only
        val parts = address.trim().split(Regex("\\s+"), limit = 2)
        val street = if (parts.size == 2 && parts[0].all { it.isDigit() }) parts[1] else address.trim()
        return street.count { it.isLetter() }
    }

    private fun nameLen(name: String): Int = name.count { it.isLetter() }

    private fun vowelCount(name: String): Int {
        val vowels = setOf('a', 'e', 'i', 'o', 'u')
        return name.lowercase().count { it in vowels }
    }

    private fun consonantCount(name: String): Int {
        val vowels = setOf('a', 'e', 'i', 'o', 'u')
        return name.lowercase().count { it.isLetter() && it !in vowels }
    }

    private fun gcd(a: Int, b: Int): Int {
        var x = abs(a)
        var y = abs(b)
        while (y != 0) {
            val t = x % y
            x = y
            y = t
        }
        return x
    }
}
