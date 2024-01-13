package io.github.ialegor.prometheus.exporter.keenetic

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
@EnableFeignClients
@ConfigurationPropertiesScan(basePackages = ["io.github.ialegor.prometheus.exporter.keenetic.config"])
class KeeneticExporterApplication

fun main(args: Array<String>) {
    runApplication<KeeneticExporterApplication>(*args)
}
