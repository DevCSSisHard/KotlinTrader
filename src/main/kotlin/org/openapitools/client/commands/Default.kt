package org.openapitools.client.commands

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import org.openapitools.client.models.GetMyShip200Response
import token

class Default: Command {
    override val commandName: String = "Default"

    override suspend fun processCommand(remainingCommands: List<String>) {
        val response: HttpResponse = HttpClientObject.client.get("https://api.spacetraders.io/v2/my/ships/RUNDA-1") {
            bearerAuth(token)
            contentType(ContentType.Application.Json)
        }
        val apiResponse : GetMyShip200Response = response.body()
        CurrentData.selectedShip = apiResponse.data
        //selectedShip = apiResponse.data
    }
}