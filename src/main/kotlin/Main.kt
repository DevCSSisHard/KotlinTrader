import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.client.plugins.contentnegotiation.*
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
    var lastShipYard = ""
    while (loop) {
        println("***** Waiting for input *****")
        val input = readLine()!!
        when  {
            input.startsWith("Purchase Ship", ignoreCase = true) -> {
                //val location = Ships.getLocationSystem(selectedShip!!)
                //Generalize This + Move to class/Function.
                val location = lastShipYard
                println("Shipyard Location: "+ location)
                println("Ship to purchase: ")
                val shipPurchase = readLine()!!
                purchaseShip(client,shipPurchase,location)
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
                //TODO: Format the returned values of ships in a function.
                if (input.equals("view ships", ignoreCase = true)) {
                    println("Warning: No shipyard provided.")
                    continue
                }
                val location = Ships.getLocationSystem(selectedShip!!)
                val splitInput = input.split(" ").toTypedArray()
                val locationToCheck = splitInput[2]
                displayShipyardShips(client, location, locationToCheck)
            }
            input.startsWith("Select Ship", ignoreCase = true) -> {
                println("Enter ship symbol to select.")
                val desiredShip = readLine()!!
                selectedShip = selectAShip(client, desiredShip)
            }
            input.startsWith("Ships", ignoreCase = true) -> {

                displayShipList(client)
            }
            input.startsWith("Ship Full Report", ignoreCase = true) -> {
                if(selectedShip == null) {
                    println("Ship Selection needed.")
                    continue
                }
                Ships.shipReport(selectedShip)
            }
            input.startsWith("Fleet Full Report", ignoreCase = true) -> {
                displayFleetReport(client)
            }
            input.startsWith("Waypoints", ignoreCase = true) -> {
                if(selectedShip == null){
                    println("WARNING: No reference ship selected. Select a ship first.")
                    continue
                }
                val location = Ships.getLocationSystem(selectedShip)
                displayWaypoints(client, location)
            }
            input.startsWith("Info", ignoreCase = true) -> {
                displayAgentInfo(client)
            }
            input.startsWith("Contracts", ignoreCase = true) -> {
                displayContracts(client)
            }
            input.startsWith("Shipyards", ignoreCase = true) -> {
                if(selectedShip == null){
                    println("WARNING: No reference ship selected. Select a ship first.")
                    continue
                }
                val location = Ships.getLocationSystem(selectedShip)
                lastShipYard = getShipYards(client, location)
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

/**
 * Function to purchase a ship.
 * @param shipPurchase String of ship to purchase, hardcoded and validated that way.
 * @param location is the location of the shipyard.
 */
suspend fun purchaseShip(client: HttpClient, shipPurchase: String, location: String) {
    when {
        shipPurchase.equals("Mining Drone") -> {
            println("Purchasing Mining Drone")
            val response: HttpResponse = client.post("https://api.spacetraders.io/v2/my/ships") {
                bearerAuth(token)
                contentType(ContentType.Application.Json)
                setBody(PurchaseShipRequest(ShipType.mININGDRONE, location))
            }
            println("Purchase Complete. " + response.status)
        }
    }
}

/**
 * Function to display ships for sale at selected shipyard.
 */
suspend fun displayShipyardShips(client: HttpClient, location: String, locationToCheck: String) {
    val response: HttpResponse = client.get("https://api.spacetraders.io/v2/systems/${location}/waypoints/${locationToCheck}/shipyard") {
        bearerAuth(token)
        contentType(ContentType.Application.Json)
    }
    val apiResponse: GetShipyard200Response = response.body()
    println(apiResponse.data.shipTypes)
}

/**
 * Function to display just a list of ships, a much more basic fleet selection.
 */
suspend fun displayShipList(client: HttpClient) {
    val response: HttpResponse = client.get("https://api.spacetraders.io/v2/my/ships") {
        bearerAuth(token)
        contentType(ContentType.Application.Json)
    }
    val apiResponse: GetMyShips200Response = response.body()
    Ships.getShips(apiResponse)
}

/**
 * Function to display an entire fleet report, in detail.
 * WARNING: May be very long.
 */
suspend fun displayFleetReport(client: HttpClient) {
    val response: HttpResponse = client.get("https://api.spacetraders.io/v2/my/ships") {
        bearerAuth(token)
        contentType(ContentType.Application.Json)
    }
    val apiResponse: GetMyShips200Response = response.body()

    for (ship in apiResponse.data) {
        Ships.shipReport(ship)
    }
}

/**
 * Function to display waypoint information
 * @param location relative to selected ship, must have a ship selected for this to work.
 */
suspend fun displayWaypoints(client: HttpClient, location: String) {
    //X1-ZA40
    val response: HttpResponse = client.get("https://api.spacetraders.io/v2/systems/${location}/waypoints") {
        bearerAuth(token)
        contentType(ContentType.Application.Json)
    }
    val apiResponse: GetSystemWaypoints200Response = response.body()
    Waypoints.getWaypoints(apiResponse)
}

/**
 * Function to display unformatted agent info alongside the agent symbol.
 * Displays account Id, Headquarters, credits and faction.
 */
suspend fun displayAgentInfo(client: HttpClient) {
    val response: HttpResponse = client.get("https://api.spacetraders.io/v2/my/agent") {
        bearerAuth(token)
        contentType(ContentType.Application.Json)
    }
    val apiResponse: GetMyAgent200Response = response.body()
    println(apiResponse)
    println(apiResponse.data.symbol)
}

/**
 * Function to display all contracts
 */
suspend fun displayContracts(client: HttpClient) {
    val response: HttpResponse = client.get("https://api.spacetraders.io/v2/my/contracts/") {
        bearerAuth(token)
        contentType(ContentType.Application.Json)
    }
    val apiResponse: GetContracts200Response = response.body()
    Contracts.getContracts(apiResponse)
}

/**
 * A function to display all shipyards, if any, in a waypoint relative to selected ship.
 * Also returns the FIRST shipyard on the list if multiple shipyards are in the same waypoint.
 *
 */
suspend fun getShipYards(client: HttpClient, location: String): String {
    val response: HttpResponse = client.get("https://api.spacetraders.io/v2/systems/${location}/waypoints") {
        bearerAuth(token)
        contentType(ContentType.Application.Json)
    }
    val apiResponse: GetSystemWaypoints200Response = response.body()
    //Waypoints.printShipYards(apiResponse)
    return Waypoints.getShipYards(apiResponse)
}

/**
 * Function to select a ship and return that object.
 * @param HttpClient the HTTPClient.
 * @param desiredShip Symbol of ship wanted to select and return ie. MYAGENT-1
 */
suspend fun selectAShip(client: HttpClient, desiredShip: String): Ship? {
    val response: HttpResponse = client.get("https://api.spacetraders.io/v2/my/ships/${desiredShip}") {
        bearerAuth(token)
        contentType(ContentType.Application.Json)
    }
    val apiResponse: GetMyShip200Response = response.body()
    return apiResponse.data
}
