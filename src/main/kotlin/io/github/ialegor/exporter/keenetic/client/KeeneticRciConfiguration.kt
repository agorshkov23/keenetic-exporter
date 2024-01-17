package io.github.ialegor.exporter.keenetic.client

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.json.JsonMapper
import feign.*
import feign.codec.Decoder
import feign.codec.ErrorDecoder
import io.github.ialegor.exporter.keenetic.config.KeeneticClientProperties
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.http.codec.json.Jackson2SmileDecoder
import org.springframework.util.MimeType
import org.springframework.util.MimeTypeUtils
import java.lang.Exception

//@Configuration
class KeeneticRciConfiguration(
    private val properties: KeeneticClientProperties
) {

    //  https://forum.ih-systems.com/topic/189/%D0%BA%D1%82%D0%BE-%D0%B4%D0%BE%D0%BC%D0%B0-%D1%87%D0%B5%D1%80%D0%B5%D0%B7-zyxel-keenetic


    //    @Bean
    fun authInterceptor(): KeeneticInterceptor {
        return KeeneticInterceptor(properties)
//        return object : RequestInterceptor {
//            private var cookie: String? = null
//
//            override fun apply(template: RequestTemplate) {
//                if (cookie == null)
//
//                    TODO("Not yet implemented")
//            }
//        }
    }

    @Bean
    fun decoder(): ObjectMapper {
        val json = JsonMapper()
            .findAndRegisterModules()

        return json
//        return Jackson2JsonDecoder(json, MimeTypeUtils.APPLICATION_JSON)
    }

    @Bean
    fun cookieHolder(): CookieHolder {
        return CookieHolder()
    }

//    @Bean
    fun logger(): Logger {
        return Logger()
    }

    class Logger : RequestInterceptor, ResponseInterceptor {
        private val log = LoggerFactory.getLogger(KeeneticRciClient::class.java)

        override fun apply(template: RequestTemplate?) {
//            System.err.println(
//                template.toString().split("\n").joinToString("\n") { "> $it" }
//            )
        }

        override fun intercept(invocationContext: InvocationContext, chain: ResponseInterceptor.Chain): Any {
//            System.err.println(
//                invocationContext.response().toString().split("\n").joinToString("\n") { "< $it" }
//            )

            return chain.next(invocationContext)
        }
    }

    class CookieHolder : RequestInterceptor, ResponseInterceptor {

        private var cookie: String? = null

        override fun apply(template: RequestTemplate) {
            if (cookie != null) {
                template.header("cookie", cookie)
            }

//            System.err.println(
//                template.toString().split("\n").joinToString("\n") { "> $it" }
//            )
        }

        override fun intercept(invocationContext: InvocationContext, chain: ResponseInterceptor.Chain): Any {
            val setCookieHeader = invocationContext.response().headers()["set-cookie"].orEmpty().firstOrNull()
            if (setCookieHeader != null) {
                this.cookie = setCookieHeader
            }

//            System.err.println(
//                invocationContext.response().toString().split("\n").joinToString("\n") { "< $it" }
//            )

            return chain.next(invocationContext)
        }

    }

//    @Bean
//    fun logger(): Logger.Level {
//        return Logger.Level.FULL
//    }

    class KeeneticInterceptor(
        private val properties: KeeneticClientProperties
    ) : RequestInterceptor, ResponseInterceptor {

        private var cookie: String? = null

        override fun apply(template: RequestTemplate) {
//            TODO("Not yet implemented")
            val i = 5
        }

        override fun intercept(invocationContext: InvocationContext, chain: ResponseInterceptor.Chain): Any {
            val response = invocationContext.response()
            if (response.status() != 401) {
                return chain.next(invocationContext)
            }

            TODO()
        }
    }

//    @Bean
//    fun unauthorizedErrorDecoder(/*defaultErrorDecoder: ErrorDecoder*/): UnauthorizedErrorDecoder {
//        val defaultErrorDecoder = ErrorDecoder.Default()
//        return UnauthorizedErrorDecoder(defaultErrorDecoder, properties)
//    }

//    class UnauthorizedErrorDecoder(
//        private val default: ErrorDecoder,
//        private val properties: KeeneticClientProperties,
//    ) : ErrorDecoder {
//
//        private val json = JsonMapper()
//
//        override fun decode(methodKey: String, response: Response): Exception {
//            if (response.status() == 401) {
//                // Extract and apply appropriate authorization credentials
//                // Resend the request
//
//                val request = response.request().requestTemplate()
//
//                val body = mapOf(
//                    "login" to properties.username,
//                    "password" to properties.password,
//                )
//
//
//                request.uri("auth")
//                    .method(Request.HttpMethod.POST)
//                    .body(json.writeValueAsString(body))
//                    .request()
//
//
//
//                val i = 5
//            }
//
//            return default.decode(methodKey, response)
//        }
//    }
}
