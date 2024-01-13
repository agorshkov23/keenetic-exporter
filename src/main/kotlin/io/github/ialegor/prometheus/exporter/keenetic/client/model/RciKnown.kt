package io.github.ialegor.prometheus.exporter.keenetic.client.model

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

//data class RciRequest(
//
//)

data class RciShowAssociationsResponse(
    val station: List<Station>
) {

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


//    gi: 400,
//    rssi: -69,
//    mcs: 4,
//    txss: 1,
//    ebf: false,
//    dl-mu: false,
//    _11: [2 elements
//"k",
//"v"
//],
//    roam: "pmk",
//    security: "wpa2-psk"
}

