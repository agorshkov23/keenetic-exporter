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

    @Scheduled(fixedRate = 10_000)
    fun tick() {
        LoggerFactory.getLogger(this::class.java).info("get client metrics")

        val metrics = service.getClientMetrics()

        clientUptime.register(
            metrics.map { MultiGauge.Row.of(it.toTags(), it.uptime.toMillis()) },
            true,
        )

        clientTx.register(
            metrics.map { MultiGauge.Row.of(it.toTags(), it.txBytes) },
            true,
        )

        clientRx.register(
            metrics.map { MultiGauge.Row.of(it.toTags(), it.rxBytes) },
            true,
        )
    }

    private fun ClientMetrics.toTags(): Tags {
        return Tags.of("mac", mac, "known", known)
    }
}
