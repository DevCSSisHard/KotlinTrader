package org.openapitools.client.commands

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import org.openapitools.client.models.DockShip200Response
import token

class Dock: Command {
    override val commandName: String = "Dock"

    override suspend fun processCommand(remainingCommands: List<String>) {
        val response: HttpResponse = HttpClientObject.client.post("https://api.spacetraders.io/v2/my/ships/${CurrentData.selectedShip!!.symbol}/dock") {
            bearerAuth(token)
            contentType(ContentType.Application.Json)
        }
        val apiResponse: DockShip200Response = response.body()
        println("Ship ${CurrentData.selectedShip!!.symbol} has docked at "+ apiResponse.data.nav.waypointSymbol)
    }
}