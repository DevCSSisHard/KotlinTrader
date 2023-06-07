package org.openapitools.client.commands

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import org.openapitools.client.apis.Contracts
import org.openapitools.client.models.GetContracts200Response
import token

class Contracts: Command {
    override val commandName: String = "Contracts"

    override suspend fun processCommand(remainingCommands: List<String>) {
        val response: HttpResponse = HttpClientObject.client.get("https://api.spacetraders.io/v2/my/contracts/") {
            bearerAuth(token)
            contentType(ContentType.Application.Json)
        }
        val apiResponse: GetContracts200Response = response.body()
        Contracts.getContracts(apiResponse)
    }
}