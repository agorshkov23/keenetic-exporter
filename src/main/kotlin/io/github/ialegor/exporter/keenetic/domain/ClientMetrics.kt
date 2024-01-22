package io.github.ialegor.exporter.keenetic.domain

import io.github.ialegor.exporter.keenetic.client.model.RciIpHotspotHost
import io.github.ialegor.exporter.keenetic.client.model.RciStationAssociation

data class ClientMetrics(
    val mac: String,
    val known: String?,
    val stationAssociations: RciShowAssociationsResponse.Station?,
    val stationAssociations: RciStationAssociation?,
    val ipHotspotHost: RciIpHotspotHost?,
)
