import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.ContentType.Application.Json
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.openapitools.client.apis.Contracts
import org.openapitools.client.apis.Ships
import org.openapitools.client.apis.Waypoints
import org.openapitools.client.models.*
import java.lang.System

val token = System.getenv("token") ?: "None"

suspend fun main(args: Array<String>) {
    var loop = true

    val client = HttpClient(CIO) {
        install(ContentNegotiation ) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }
    println("\t***** Connected *****")
    var selectedShip : Ship? = null
    while (loop) {
        println("***** Waiting for input *****")
        val input = readLine()!!
        when(input.lowercase()) {
            "Select Ship".lowercase() -> {
                println("Enter ship symbol to select.")
                val desiredShip = readLine()!!
                val response: HttpResponse = client.get("https://api.spacetraders.io/v2/my/ships/${desiredShip}") {
                    bearerAuth(token)
                    contentType(ContentType.Application.Json)
                }
                val apiResponse: GetMyShip200Response = response.body()
                selectedShip = apiResponse.data
            }
            "Ships".lowercase() -> {
                val response: HttpResponse = client.get("https://api.spacetraders.io/v2/my/ships") {
                    bearerAuth(token)
                    contentType(ContentType.Application.Json)
                }
                val apiResponse: GetMyShips200Response = response.body()
                Ships.getShips(apiResponse)

            }
            "Ship Full Report".lowercase() -> {
                if(selectedShip == null) {
                    println("Ship Selection needed.")
                    continue
                }
                Ships.shipReport(selectedShip)
            }
            "Fleet Full Report".lowercase() -> {
                val response: HttpResponse = client.get("https://api.spacetraders.io/v2/my/ships") {
                    bearerAuth(token)
                    contentType(ContentType.Application.Json)
                }
                val apiResponse: GetMyShips200Response = response.body()

                for (ship in apiResponse.data) {
                    Ships.shipReport(ship)
                }
            }
            "Waypoints".lowercase() -> {
                //X1-ZA40
                val response: HttpResponse = client.get("https://api.spacetraders.io/v2/systems/X1-VS75/waypoints") {
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
                println(apiResponse.data.symbol)
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
                if(selectedShip == null){
                    println("WARNING: No reference ship selected. Select a ship first.")
                    continue
                }
                val location = Ships.getLocationSystem(selectedShip)
                val response: HttpResponse = client.get("https://api.spacetraders.io/v2/systems/${location}/waypoints") {
                    bearerAuth(token)
                    contentType(ContentType.Application.Json)
                }
                val apiResponse: GetSystemWaypoints200Response = response.body()
                Waypoints.printShipYards(apiResponse)
            }
            "Quit".lowercase() -> {
                //haha
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