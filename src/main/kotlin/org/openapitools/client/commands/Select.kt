package org.openapitools.client.commands

import CurrentData
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import org.openapitools.client.models.GetMyShip200Response
import token

class Select: Command {
    override val commandName: String = "Select"

    override suspend fun processCommand(remainingCommands: List<String>) {
        print(remainingCommands)
        if (remainingCommands[0] == "ship") {
            println("Enter ship symbol to select.")
            val desiredShip = readLine()!!
            val response: HttpResponse = HttpClientObject.client.get("https://api.spacetraders.io/v2/my/ships/${desiredShip}") {
                bearerAuth(token)
                contentType(ContentType.Application.Json)
            }
            val apiResponse: GetMyShip200Response = response.body()
            CurrentData.selectedShip = apiResponse.data
        }
    }
}