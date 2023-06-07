package org.openapitools.client.commands

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import org.openapitools.client.models.OrbitShip200Response
import token

class Undock: Command {
    override val commandName: String = "Undock"

    override suspend fun processCommand(remainingCommands: List<String>) {
        val response: HttpResponse = HttpClientObject.client.post("https://api.spacetraders.io/v2/my/ships/${CurrentData.selectedShip!!.symbol}/orbit") {
            bearerAuth(token)
            contentType(ContentType.Application.Json)
        }
        val apiResponse: OrbitShip200Response = response.body()
        println("${CurrentData.selectedShip!!.symbol} has entered orbit around "+apiResponse.data.nav.waypointSymbol)
    }
}