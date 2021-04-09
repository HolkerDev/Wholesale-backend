package eu.holker.wholesale

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WholesaleBackendApplication

fun main(args: Array<String>) {
    runApplication<WholesaleBackendApplication>(*args)
}
