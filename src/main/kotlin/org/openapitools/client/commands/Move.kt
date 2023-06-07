package org.openapitools.client.commands

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import org.openapitools.client.models.NavigateShip200Response
import org.openapitools.client.models.NavigateShipRequest
import token

class Move : Command {
    override val commandName: String = "Move"

    override suspend fun processCommand(remainingCommands: List<String>) {
        val locationToGo = remainingCommands[2]
        val shipSymbol = remainingCommands[1]
        navigateToLocation(HttpClientObject.client, locationToGo, shipSymbol)
    }

    private suspend fun navigateToLocation(client: HttpClient, locationToGo: String, shipSymbol: String) {
        val response: HttpResponse = HttpClientObject.client.post("https://api.spacetraders.io/v2/my/ships/${shipSymbol}/navigate") {
            bearerAuth(token)
            contentType(ContentType.Application.Json)
            setBody(NavigateShipRequest(locationToGo))
        }
        val apiResponse: NavigateShip200Response = response.body()

        println("Ship en-route to: "+ apiResponse.data.nav.route.destination)
        println("Expected arrival at :"+apiResponse.data.nav.route.arrival)
        println("Ship Status: "+apiResponse.data.nav.status + " Flight mode: "+apiResponse.data.nav.flightMode)
    }
}