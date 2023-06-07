package org.openapitools.client.commands

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import org.openapitools.client.models.GetMyAgent200Response
import token

class Info: Command {
    override val commandName: String = "Info"

    override suspend fun processCommand(remainingCommands: List<String>) {
        val response: HttpResponse = HttpClientObject.client.get("https://api.spacetraders.io/v2/my/agent") {
            bearerAuth(token)
            contentType(ContentType.Application.Json)
        }
        val apiResponse: GetMyAgent200Response = response.body()
        println(apiResponse)
        println(apiResponse.data.symbol)
    }
}