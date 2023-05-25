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
        when  {
            input.startsWith("Purchase Ship", ignoreCase = true) -> {
                //val location = Ships.getLocationSystem(selectedShip!!)
                //Generalize This + Move to class/Function.
                val location = "X1-VS75-97637F"
                println("Shipyard Location: "+ location)
                println("Ship to purchase: ")
                val shipPurchase = readLine()!!
                when {
                    shipPurchase.equals("Mining Drone") -> {
                        println("Purchasing Mining Drone")
                        val response: HttpResponse = client.post("https://api.spacetraders.io/v2/my/ships") {
                            bearerAuth(token)
                            contentType(ContentType.Application.Json)
                            setBody(PurchaseShipRequest(ShipType.mININGDRONE, location))
                        }
                        println("Purchase Complete. " + response.status)
                        //val apiResponse : PurchaseShip201Response = response.body()
                        //println(apiResponse.data.agent)
                        //println(apiResponse.data.transaction)
                        //println(apiResponse.data.ship)
                    }
                }
            }
            input.startsWith("Default", ignoreCase = true) -> {
                val response: HttpResponse = client.get("https://api.spacetraders.io/v2/my/ships/RUNDA-1") {
                    bearerAuth(token)
                    contentType(ContentType.Application.Json)
                }
                val apiResponse : GetMyShip200Response = response.body()
                selectedShip = apiResponse.data
            }
            input.startsWith("View Ships", ignoreCase = true) -> {
                if (input.equals("view ships", ignoreCase = true)) {
                    println("Warning: No shipyard provided.")
                    continue
                }
                val location = Ships.getLocationSystem(selectedShip!!)
                val splitInput = input.split(" ").toTypedArray()
                val locationToCheck = splitInput[2]
                val response: HttpResponse = client.get("https://api.spacetraders.io/v2/systems/${location}/waypoints/${locationToCheck}/shipyard") {
                    bearerAuth(token)
                    contentType(ContentType.Application.Json)
                }
                val apiResponse: GetShipyard200Response = response.body()
                println(apiResponse.data.shipTypes)
            }
            input.startsWith("Select Ship", ignoreCase = true) -> {
                println("Enter ship symbol to select.")
                val desiredShip = readLine()!!
                val response: HttpResponse = client.get("https://api.spacetraders.io/v2/my/ships/${desiredShip}") {
                    bearerAuth(token)
                    contentType(ContentType.Application.Json)
                }
                val apiResponse: GetMyShip200Response = response.body()
                selectedShip = apiResponse.data
            }
            input.startsWith("Ships", ignoreCase = true) -> {
                val response: HttpResponse = client.get("https://api.spacetraders.io/v2/my/ships") {
                    bearerAuth(token)
                    contentType(ContentType.Application.Json)
                }
                val apiResponse: GetMyShips200Response = response.body()
                Ships.getShips(apiResponse)

            }
            input.startsWith("Ship Full Report", ignoreCase = true) -> {
                if(selectedShip == null) {
                    println("Ship Selection needed.")
                    continue
                }
                Ships.shipReport(selectedShip)
            }
            input.startsWith("Fleet Full Report", ignoreCase = true) -> {
                val response: HttpResponse = client.get("https://api.spacetraders.io/v2/my/ships") {
                    bearerAuth(token)
                    contentType(ContentType.Application.Json)
                }
                val apiResponse: GetMyShips200Response = response.body()

                for (ship in apiResponse.data) {
                    Ships.shipReport(ship)
                }
            }
            input.startsWith("Waypoints", ignoreCase = true) -> {
                //X1-ZA40
                val response: HttpResponse = client.get("https://api.spacetraders.io/v2/systems/X1-VS75/waypoints") {
                    bearerAuth(token)
                    contentType(ContentType.Application.Json)
                }
                val apiResponse: GetSystemWaypoints200Response = response.body()
                Waypoints.getWaypoints(apiResponse)
            }
            input.startsWith("Info", ignoreCase = true) -> {
                val response: HttpResponse = client.get("https://api.spacetraders.io/v2/my/agent") {
                    bearerAuth(token)
                    contentType(ContentType.Application.Json)
                }
                val apiResponse: GetMyAgent200Response = response.body()
                println(apiResponse)
                println(apiResponse.data.symbol)
            }
            input.startsWith("Contracts", ignoreCase = true) -> {
                val response: HttpResponse = client.get("https://api.spacetraders.io/v2/my/contracts/") {
                    bearerAuth(token)
                    contentType(ContentType.Application.Json)
                }
                val apiResponse: GetContracts200Response = response.body()
                Contracts.getContracts(apiResponse)
            }
            input.startsWith("Shipyards", ignoreCase = true) -> {
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
            input.startsWith("Quit", ignoreCase = true) -> {
                //haha
                loop = false
                break
            }
            else -> println("Error with input.")
        }
    }
    client.close()
}
