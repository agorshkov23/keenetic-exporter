package io.github.ialegor.exporter.keenetic

import io.github.ialegor.exporter.keenetic.domain.ClientMetrics
import io.github.ialegor.exporter.keenetic.service.KeeneticService
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.MultiGauge
import io.micrometer.core.instrument.Tags
import io.micrometer.core.instrument.binder.BaseUnits
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.Scheduled

@Configuration
class KeeneticMeterConfiguration(
    private val service: KeeneticService,
    registry: MeterRegistry,
) {

    private val clientUptime = MultiGauge
        .builder("keenetic.client.uptime")
        .description("Keenetic Router client uptime millis")
        .baseUnit(BaseUnits.MILLISECONDS)
        .register(registry)

    private val clientTx = MultiGauge
        .builder("keenetic.client.tx")
        .description("Keenetic Router client tx bytes")
        .baseUnit(BaseUnits.BYTES)
        .register(registry)

    private val clientRx = MultiGauge
        .builder("keenetic.client.rx")
        .description("Keenetic Router client rx bytes")
        .baseUnit(BaseUnits.BYTES)
        .register(registry)

    private val clientTxRate = MultiGauge
        .builder("keenetic.client.txrate")
        .description("Keenetic Router client tx rate")
        .baseUnit("mbps")
        .register(registry)

    private val clientRssi = MultiGauge
        .builder("keenetic.client.rssi")
        .description("Keenetic Router client signal level")
        .baseUnit("dbi")
        .register(registry)

    @Scheduled(fixedRate = 10_000)
    fun tick() {
        LoggerFactory.getLogger(this::class.java).info("get client metrics")

        val metrics = service.getClientMetrics()

        val clientUptimeList = mutableListOf<MultiGauge.Row<Number>>()
        val clientTxList = mutableListOf<MultiGauge.Row<Number>>()
        val clientRxList = mutableListOf<MultiGauge.Row<Number>>()
        val clientTxRateList = mutableListOf<MultiGauge.Row<Number>>()
        val clientRssiList = mutableListOf<MultiGauge.Row<Number>>()

        for (metric in metrics) {
            if (metric.stationAssociations != null) {
                clientUptimeList += MultiGauge.Row.of(metric.toTags(), metric.stationAssociations.uptime * 1000)
                clientTxList += MultiGauge.Row.of(metric.toTags(), metric.stationAssociations.txbytes)
                clientRxList += MultiGauge.Row.of(metric.toTags(), metric.stationAssociations.rxbytes)
                clientTxRateList += MultiGauge.Row.of(metric.toTags(), metric.stationAssociations.txrate)
                clientRssiList += MultiGauge.Row.of(metric.toTags(), metric.stationAssociations.rssi)
            }
        }

        clientUptime.register(clientUptimeList, true)
        clientTx.register(clientTxList, true)
        clientRx.register(clientRxList, true)
        clientTxRate.register(clientTxRateList, true)
        clientRssi.register(clientRssiList, true)

//        clientUptime.register(
//            metrics.map { MultiGauge.Row.of(it.toTags(), it.stationAssociations?.uptime?.let { it * 1000 })) },
//            true,
//        )
//
//        clientTx.register(
//            metrics.map { MultiGauge.Row.of(it.toTags(), it.txBytes) },
//            true,
//        )
//
//        clientRx.register(
//            metrics.map { MultiGauge.Row.of(it.toTags(), it.rxBytes) },
//            true,
//        )
    }

    private fun ClientMetrics.toTags(): Tags {
        return Tags.of("mac", mac, "known", known)
    }
}
