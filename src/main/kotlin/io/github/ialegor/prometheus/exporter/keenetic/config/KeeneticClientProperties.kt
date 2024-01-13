package io.github.ialegor.prometheus.exporter.keenetic.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "exporter.keenetic.client")
data class KeeneticClientProperties(
    val url: String,
    val username: String,
    val password: String,
)
