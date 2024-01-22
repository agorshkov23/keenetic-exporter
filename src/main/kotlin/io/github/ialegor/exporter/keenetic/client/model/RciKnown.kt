package io.github.ialegor.exporter.keenetic.client.model

import com.fasterxml.jackson.annotation.JsonProperty

data class RciKnownResponse(
    val host: Map<String, RciMac>,
)

data class RciMac(
    val mac: String,
)

data class AuthRequest(
    val login: String,
    val password: String,
)

data class RciShowAssociationsResponse(
    val station: List<RciStationAssociation>,
)

data class RciIpHotspotHost(
    val mac: String,
    val via: String,
    val ip: String,
    val hostname: String,
    val name: String,
    val active: Boolean,
    val rxbytes: Long,
    val txbytes: Long,
    val uptime: Int,

    val speed: Int?,
    val ssid: String?,
    val ap: String?,
    val txrate: Int?,
    val mode: String?,
    val security: String?,
)

