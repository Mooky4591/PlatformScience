package com.scottrobinson.platformscience.driverlist.domain.parser

import org.junit.Assert.*
import org.junit.Test
import org.json.JSONException
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ShipmentDriverParserImplTest {
    private val parser = ShipmentDriverParserImpl()

    @Test
    fun `parse valid json returns correct payload`() {
        val json = """
            {"drivers": ["Alice", "Bob"], "shipments": ["NY", "LA"]}
        """.trimIndent()
        val payload = parser.parse(json)
        assertEquals(listOf("Alice", "Bob"), payload.drivers)
        assertEquals(listOf("NY", "LA"), payload.shipments)
    }

    @Test
    fun `parse empty arrays returns empty lists`() {
        val json = """
            {"drivers": [], "shipments": []}
        """.trimIndent()
        val payload = parser.parse(json)
        assertTrue(payload.drivers.isEmpty())
        assertTrue(payload.shipments.isEmpty())
    }

    @Test(expected = JSONException::class)
    fun `parse missing drivers throws exception`() {
        val json = """
            {"shipments": ["NY"]}
        """.trimIndent()
        parser.parse(json)
    }

    @Test(expected = JSONException::class)
    fun `parse missing shipments throws exception`() {
        val json = """
            {"drivers": ["Alice"]}
        """.trimIndent()
        parser.parse(json)
    }

    @Test(expected = JSONException::class)
    fun `parse malformed json throws exception`() {
        val json = "not a json"
        parser.parse(json)
    }
}
