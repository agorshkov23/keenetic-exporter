package io.github.ialegor.io.github.ialegor.exporter.keenetic

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KeeneticExporterApplication

fun main(args: Array<String>) {
    runApplication<KeeneticExporterApplication>(*args)
}
