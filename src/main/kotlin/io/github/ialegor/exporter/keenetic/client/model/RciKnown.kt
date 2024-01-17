package io.github.ialegor.exporter.keenetic.client.model

import com.fasterxml.jackson.annotation.JsonProperty

data class RciKnownResponse(
    val host: Map<String, RciMac>
)

data class RciMac(
    val mac: String
)

data class AuthRequest(
    val login: String,
    val password: String,
)

data class RciShowAssociationsResponse(
    val station: List<Station>
) {

    //  https://help.keenetic.com/hc/ru/articles/115000046069
    //  TODO: rename to RciStationAssociation
    data class Station(
        val mac: String,
        val ap: String,
        val psm: Boolean,
        val authenticated: Boolean,
        val txrate: Int,
        val uptime: Int,
        val txbytes: Long,
        val rxbytes: Long,
        val ht: Int,
        val mode: String,
        val gi: Int,
        val rssi: Int,
        val mcs: Int,
        val txss: Int,
        val ebf: String,
        @JsonProperty("dl-mu")
        val dl_mu: String,
        val _11: List<String>?,
        val roam: String?,
        val security: String,
    )
}

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

