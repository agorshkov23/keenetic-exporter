package io.github.ialegor.exporter.keenetic.domain

import io.github.ialegor.exporter.keenetic.client.model.RciShowAssociationsResponse
import java.time.Duration

data class ClientMetrics(
    val mac: String,
    val known: String?,
    val stationAssociations: RciShowAssociationsResponse.Station?,
    @Deprecated("Use stationAssociations.uptime")
    val uptime: Duration,
    @Deprecated("Use stationAssociations.txbytes")
    val txBytes: Long,
    @Deprecated("Use stationAssociations.rxbytes")
    val rxBytes: Long,
    @Deprecated("Use stationAssociations.txrate")
    val txRate: Int,
)
