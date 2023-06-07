package org.openapitools.client.commands

import CurrentData
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import org.openapitools.client.models.ExtractResources201Response
import org.openapitools.client.models.ExtractResourcesRequest
import token

class Extract : Command {
    override val commandName: String = "Extract"

    override suspend fun processCommand(remainingCommands: List<String>) {
        extractResources(HttpClientObject.client,CurrentData.selectedShip!!.symbol)

    }

    private suspend fun extractResources(client: HttpClient, symbol: String) {
        val response: HttpResponse = HttpClientObject.client.post("https://api.spacetraders.io/v2/my/ships/${symbol}/extract") {
            bearerAuth(token)
            contentType(ContentType.Application.Json)
            setBody(ExtractResourcesRequest(survey = null))
        }
        if (!response.status.isSuccess()) {
            println("Error with extracting resources, drones cooling down.")
            return
        } else {
            val apiResponse: ExtractResources201Response = response.body()
            println(apiResponse)
            println("Extracted "+ apiResponse.data.extraction.yield.units + " units of "+apiResponse.data.extraction.yield.symbol)
        }
    }
}