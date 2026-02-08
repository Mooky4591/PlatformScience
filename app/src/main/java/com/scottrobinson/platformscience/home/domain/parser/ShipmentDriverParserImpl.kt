package com.scottrobinson.platformscience.home.domain.parser

import com.scottrobinson.platformscience.home.domain.dtos.Payload
import org.json.JSONObject
import javax.inject.Inject

class ShipmentDriverParserImpl @Inject constructor() : ShipmentDriverParser {
    override fun parse(jsonText: String): Payload {
        val root = JSONObject(jsonText)
        val driversArr = root.getJSONArray("drivers")
        val shipmentsArr = root.getJSONArray("shipments")

        val drivers = buildList(driversArr.length()) {
            for (i in 0 until driversArr.length()) add(driversArr.getString(i))
        }
        val shipments = buildList(shipmentsArr.length()) {
            for (i in 0 until shipmentsArr.length()) add(shipmentsArr.getString(i))
        }

        return Payload(drivers = drivers, shipments = shipments)
    }
}