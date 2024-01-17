package io.github.ialegor.exporter.keenetic.service

import feign.FeignException
import io.github.ialegor.exporter.keenetic.client.KeeneticRciClient
import io.github.ialegor.exporter.keenetic.client.model.AuthRequest
import io.github.ialegor.exporter.keenetic.client.model.RciIpHotspotHost
import io.github.ialegor.exporter.keenetic.client.model.RciKnownResponse
import io.github.ialegor.exporter.keenetic.client.model.RciShowAssociationsResponse
import io.github.ialegor.exporter.keenetic.config.KeeneticClientProperties
import io.github.ialegor.exporter.keenetic.domain.ClientMetrics
import org.springframework.stereotype.Service
import java.security.MessageDigest

@Service
class KeeneticService(
    private val client: KeeneticRciClient,
    private val properties: KeeneticClientProperties,
) {

    fun getClientMetrics(): List<ClientMetrics> {
        val known = getKnown()
        val macToKnownMap = known.host.entries.associateBy { it.value.mac }.mapValues { it.value.key }

        val associations = getShowAssociations()
        val macToStationAssociationMap = associations.station.associateBy { it.mac }

        val ipHotspotHosts = getIpHotspotHosts()
        val macToIpHotspotHostMap = ipHotspotHosts.associateBy { it.mac }

        val macs = sequence<String> {
            yieldAll(macToKnownMap.keys)
            yieldAll(macToStationAssociationMap.keys)
            yieldAll(macToIpHotspotHostMap.keys)
        }.toSet()

        return macs.map {  mac ->
            ClientMetrics(mac, macToKnownMap[mac], macToStationAssociationMap[mac], macToIpHotspotHostMap[mac])
        }
    }

    private fun getIpHotspotHosts(): List<RciIpHotspotHost> {
        return handle { client.getIpHotspotHosts() }
    }

    private fun getShowAssociations(): RciShowAssociationsResponse {
        return handle { client.getShowAssociations() }
    }

    fun getKnown(): RciKnownResponse {
        return handle { client.getKnown() }
    }

    private fun auth() {
        try {
            val auth1 = client.getAuth()
//            val auth = client.auth(AuthRequest(properties.username, properties.password))

        } catch (e: FeignException.Unauthorized) {
            token = e.responseHeaders()["x-ndm-challenge"]!!.first()
            realm = e.responseHeaders()["x-ndm-realm"]!!.first()
            val cookies = e.responseHeaders()["set-cookie"]!!.first().split("; ")
            val (sessionCookie, sessionId) = cookies.first().split("=")

            this.sessionId = sessionId
//            this.sessionId = sessionCookie

//            val cookie = "_authorized=${properties.username}; sysmode=router; session_id=$sessionId"

            try {
                client.auth(
//                    cookie,
                    AuthRequest(properties.username, hash()),
                )
            } catch (e: Exception) {
                val i = 5
            }

//            throw kotlin.IllegalArgumentException("Invalid username or password!")
        } catch (e: Exception) {
            val i = 5
        }
    }

    var realm = ""
    var token = ""
    var sessionId = ""

    private fun hash(): String {
        val login = properties.username
        val password = properties.password

        val a = "$login:$realm:$password"
        val md5 = MessageDigest.getInstance("MD5")
        val b = md5.digest(a.toByteArray()).joinToString("") { "%02x".format(it) }
        val c = token + b
        val sha256 = MessageDigest.getInstance("SHA-256")
        val d = sha256.digest(c.toByteArray()).joinToString("") { "%02x".format(it) }
        return d
    }

    private fun <TResult> handle(handler: () -> TResult): TResult {
        try {
            val result = handler.invoke()
            return result
        } catch (e: FeignException.Unauthorized) {
            auth()
            return handler.invoke()
        }
    }
}
