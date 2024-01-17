package io.github.ialegor.exporter.keenetic.client

import io.github.ialegor.exporter.keenetic.client.model.AuthRequest
import io.github.ialegor.exporter.keenetic.client.model.RciIpHotspotHost
import io.github.ialegor.exporter.keenetic.client.model.RciKnownResponse
import io.github.ialegor.exporter.keenetic.client.model.RciShowAssociationsResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader

@FeignClient(name = "keenetic-rci", configuration = [KeeneticRciConfiguration::class])
interface KeeneticRciClient : KeeneticClient {

//    @PostMapping("/rci")
//    fun getRci(body: RciRequest): RciResponse

    @GetMapping("/rci/known")
    fun getKnown(): RciKnownResponse

    @GetMapping("/rci/show/associations")
    fun getShowAssociations(): RciShowAssociationsResponse

    @GetMapping("/rci/show/ip/hotspot/host")
    fun getIpHotspotHosts(): List<RciIpHotspotHost>
}

interface KeeneticClient {

    @GetMapping("/auth")
    fun getAuth(): ResponseEntity<Any>

    @PostMapping("/auth")
    fun auth(@RequestBody body: AuthRequest): ResponseEntity<Any>
}
