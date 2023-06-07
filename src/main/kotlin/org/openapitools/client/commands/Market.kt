package org.openapitools.client.commands

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import org.openapitools.client.apis.Markets
import org.openapitools.client.models.GetMarket200Response
import org.openapitools.client.models.Ship
import token

class Market : Command {
    override val commandName: String = "Market"

    override suspend fun processCommand(remainingCommands: List<String>) {
        if (CurrentData.selectedShip != null) {
            if (CurrentData.selectedShip!!.nav.status.value != "DOCKED") {
                viewMarket(HttpClientObject.client, CurrentData.selectedShip!!, 0)
            } else {
                viewMarket(HttpClientObject.client, CurrentData.selectedShip!!, 1)
            }
        } else {
            println("Error with ship selection, be sure a ship is selected.")
            return
        }

    }

    private suspend fun viewMarket(client: HttpClient, selectedShip: Ship, i: Int) {
        if (selectedShip == null) {
            println("Ship Selection Error")
            return
        }
        if (i == 0) {
            val shipSystem = selectedShip.nav.systemSymbol
            val shipLocation = selectedShip.nav.waypointSymbol
            val response: HttpResponse =
                HttpClientObject.client.get("https://api.spacetraders.io/v2/systems/${shipSystem}/waypoints/${shipLocation}/market") {
                    bearerAuth(token)
                    contentType(ContentType.Application.Json)
                }
            val apiResponse: GetMarket200Response = response.body()
            //println(apiResponse)
            Markets.viewMarkets(apiResponse)
        }
        if (i == 1) {
            val shipSystem = selectedShip.nav.systemSymbol
            val shipLocation = selectedShip.nav.waypointSymbol
            val response: HttpResponse =
                HttpClientObject.client.get("https://api.spacetraders.io/v2/systems/${shipSystem}/waypoints/${shipLocation}/market") {
                    bearerAuth(token)
                    contentType(ContentType.Application.Json)
                }
            val apiResponse: GetMarket200Response = response.body()
            Markets.viewMarkets(apiResponse)
            Markets.viewTransactions(apiResponse)
        }
    }
}