package io.github.ialegor.prometheus.exporter.keenetic.domain

import java.time.Duration

data class ClientMetrics(
    val mac: String,
    val known: String?,
    val uptime: Duration,
    val txBytes: Long,
    val rxBytes: Long,
)
