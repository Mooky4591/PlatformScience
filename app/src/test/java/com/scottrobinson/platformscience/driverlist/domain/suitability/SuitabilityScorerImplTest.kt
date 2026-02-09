package com.scottrobinson.platformscience.driverlist.domain.suitability

import org.junit.Assert.assertEquals
import org.junit.Test

class SuitabilityScorerImplTest {
    private val scorer = SuitabilityScorerImpl()

    @Test
    fun `score returns correct value for even street name length, no common factors`() {
        // "Main St" -> "Main St" (remove number), 6 letters (even)
        // Driver: "Alice" (3 vowels)
        // base = 3 * 1.5 = 4.5
        // gcd(6,5) = 1, so no 50% bonus
        val result = scorer.score("Alice", "Main St")
        assertEquals(4.5, result, 0.0001)
    }

    @Test
    fun `score returns correct value for even street name length, with and without common factors`() {
        // No common factors:
        // "Ellen" (2 vowels, 5 letters), "Market St" (8 letters, even)
        // base = 2 * 1.5 = 3.0, gcd(8,5)=1, so no bonus
        assertEquals(3.0, scorer.score("Ellen", "Market St"), 0.0001)

        // With common factors:
        // "Ellen" (2 vowels, 5 letters), "Ellen Ellen" (10 letters, even)
        // base = 2 * 1.5 = 3.0, gcd(10,5)=5, so 50% bonus: 3.0 * 1.5 = 4.5
        assertEquals(4.5, scorer.score("Ellen", "Ellen Ellen"), 0.0001)
    }

    @Test
    fun `score returns correct value for odd street name length`() {
        // "Broadway" (8 letters, even), "Bob" (1 vowel, 3 letters)
        // base = 1*1.5 = 1.5, gcd(8,3)=1, so no bonus
        assertEquals(1.5, scorer.score("Bob", "Broadway"), 0.0001)
        // "Broadway Ave" (11 letters, odd), "Bob" (1 vowel, 3 letters, 2 consonants)
        // base = 2*1.0 = 2.0, gcd(11,3)=1
        assertEquals(2.0, scorer.score("Bob", "Broadway Ave"), 0.0001)
    }

    @Test
    fun `score returns correct value for odd street name length, with common factors`() {
        // "Smith Rd" (7 letters, odd), "Greg" (1 vowel, 4 letters, 3 consonants)
        // base = 3*1.0 = 3.0, gcd(7,4)=1
        assertEquals(3.0, scorer.score("Greg", "Smith Rd"), 0.0001)
        // "Smith Smith" (10 letters, even), "Greg" (1 vowel, 4 letters, 3 consonants)
        // base = 1*1.5 = 1.5, gcd(10,4)=2, so bonus
        assertEquals(2.25, scorer.score("Greg", "Smith Smith"), 0.0001)
    }

    @Test
    fun `score handles street numbers and whitespace`() {
        // "123 Main St" -> "Main St" (6 letters, even), "Anna" (2 vowels, 2 consonants)
        // base = 2*1.5 = 3.0, gcd(6,4)=2, so bonus
        assertEquals(4.5, scorer.score("Anna", "123 Main St"), 0.0001)
        // "  456  Oak Ave  " -> "Oak Ave" (6 letters, even), "Sam" (1 vowel, 2 consonants)
        // base = 1*1.5 = 1.5, gcd(6,3)=3, so bonus
        assertEquals(2.25, scorer.score("Sam", "  456  Oak Ave  "), 0.0001)
    }

    @Test
    fun `score handles edge cases`() {
        // Empty driver name or shipment destination
        assertEquals(0.0, scorer.score("", ""), 0.0001)
        assertEquals(0.0, scorer.score("", "123"), 0.0001)
        assertEquals(0.0, scorer.score("123", ""), 0.0001)
        // All vowels
        assertEquals(7.5, scorer.score("aeiou", "Even St"), 0.0001) // 5 vowels * 1.5
        // All consonants, odd street name length
        assertEquals(5.0, scorer.score("bcdfg", "Oddityy"), 0.0001) // 5 consonants * 1.0
    }
}
