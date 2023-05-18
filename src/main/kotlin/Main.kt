import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import org.openapitools.client.*
import org.openapitools.client.apis.Contracts
import org.openapitools.client.apis.Waypoints
import org.openapitools.client.models.*
import java.lang.System

val token = System.getenv("token") ?: "None"

suspend fun main(args: Array<String>) {
    var loop = true
    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }
    println("\t***** Connected *****")
    while (loop) {
        println("***** Waiting for input *****")
        val input = readLine()!!
        when(input.lowercase()) {
            "Waypoints".lowercase() -> {
                val response: HttpResponse = client.get("https://api.spacetraders.io/v2/systems/X1-ZA40/waypoints") {
                    bearerAuth(token)
                    contentType(ContentType.Application.Json)
                }
                val apiResponse: GetSystemWaypoints200Response = response.body()
                Waypoints.getWaypoints(apiResponse)
            }
            "Info".lowercase() -> {
                val response: HttpResponse = client.get("https://api.spacetraders.io/v2/my/agent") {
                    bearerAuth(token)
                    contentType(ContentType.Application.Json)
                }
                val apiResponse: GetMyAgent200Response = response.body()
                println(apiResponse)
            }
            "Contracts".lowercase() -> {
                val response: HttpResponse = client.get("https://api.spacetraders.io/v2/my/contracts/") {
                    bearerAuth(token)
                    contentType(ContentType.Application.Json)
                }
                val apiResponse: GetContracts200Response = response.body()
                Contracts.getContracts(apiResponse)
            }
            "Shipyards".lowercase() -> {
                /*
                TODO: Get current selected ship location, will require a prior ship selection + default ship assumed.
                https://spacetraders.stoplight.io/docs/spacetraders/800936299c838-get-ship
                Grab data.nav.systemSymbol & data.nav.waypointSymbol and feed them into
                https://spacetraders.stoplight.io/docs/spacetraders/58e66f2fa8c82-get-waypoint
                 */
                val response: HttpResponse = client.get("https://api.spacetraders.io/v2/systems/X1-ZA40/waypoints") {
                    bearerAuth(token)
                    contentType(ContentType.Application.Json)
                }
                val apiResponse: GetSystemWaypoints200Response = response.body()
                Waypoints.printShipYards(apiResponse)
            }
            "Quit".lowercase() -> {
                loop = false
                break
            }
            else -> println("Error with input.")
        }
    }
    client.close()
}

// Check webpage response.
//println(response)
//Contracts.getContracts(apiResponse)
//Waypoints.getWaypoints(apiResponse)

/*  Functional contract grabbing

val response: HttpResponse = client.get("https://api.spacetraders.io/v2/my/contracts/") {
        bearerAuth(token)
        contentType(ContentType.Application.Json)
    }

 */


/*  Functional Location check of specified waypoint.

val response: HttpResponse = client.get("https://api.spacetraders.io/v2/systems/X1-ZA40/waypoints/X1-ZA40-15970B") {
        bearerAuth(token)
        contentType(ContentType.Application.Json)
    }
    val apiResponse: GetWaypoint200Response = response.body()
    println(apiResponse)

 */