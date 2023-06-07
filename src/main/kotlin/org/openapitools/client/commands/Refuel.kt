package org.openapitools.client.commands

import CurrentData
import commands
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import org.openapitools.client.models.RefuelShip200Response
import token

class Refuel: Command {
    override val commandName: String= "Refuel"

    override suspend fun processCommand(remainingCommands: List<String>) {
            val response: HttpResponse = HttpClientObject.client.post("https://api.spacetraders.io/v2/my/ships/${CurrentData.selectedShip!!.symbol}/refuel") {
            bearerAuth(token)
            contentType(ContentType.Application.Json)
        }
        val apiResponse: RefuelShip200Response = response.body()
        if(apiResponse.data.transaction.totalPrice > 0) {
            println("Purchased "+apiResponse.data.transaction.units +" units of fuel at "+ apiResponse.data.transaction.pricePerUnit+ " per unit.")
        } else {
            println("Validate waypoint has a market, or ship is not full on fuel. Error with purchase." + apiResponse.data + " " + response.status)
        }
    }
}