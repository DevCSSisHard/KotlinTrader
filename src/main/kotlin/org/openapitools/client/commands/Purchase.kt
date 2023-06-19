package org.openapitools.client.commands

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import org.openapitools.client.models.PurchaseShipRequest
import org.openapitools.client.models.ShipType
import token

class Purchase: Command {
    override val commandName: String="Purchase"

    override suspend fun processCommand(remainingCommands: List<String>) {
        if(remainingCommands[0] == "ship"){
            val location = CurrentData.lastShipYard
            println("Shipyard Location: "+ location)
            println("Ship to purchase: ")
            val shipPurchase = readLine()!!
            purchaseShip(HttpClientObject.client,shipPurchase,location)
        }
    }

    private suspend fun purchaseShip(client: HttpClient, shipPurchase: String, location: String) {
        print(shipPurchase)
        when {
            shipPurchase.equals("mining drone") -> {
                println("Purchasing Mining Drone")
                val response: HttpResponse = HttpClientObject.client.post("https://api.spacetraders.io/v2/my/ships") {
                    bearerAuth(token)
                    contentType(ContentType.Application.Json)
                    setBody(PurchaseShipRequest(ShipType.mININGDRONE, location))
                }
                val rawContent = response.bodyAsText()
                print(rawContent)
                println("Purchase Complete. " + response.status)
            }
        }
    }
}