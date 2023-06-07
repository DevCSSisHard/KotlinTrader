package org.openapitools.client.commands

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import org.openapitools.client.apis.Ships
import org.openapitools.client.models.GetMyShips200Response
import token

class Ships : Command{
    override val commandName: String = "Ships"

    override suspend fun processCommand(remainingCommands: List<String>) {
        println("Place Holder stuff but inside Ships Command: \n")
        val response: HttpResponse = HttpClientObject.client.get("https://api.spacetraders.io/v2/my/ships") {
            bearerAuth(token)
            contentType(ContentType.Application.Json)
        }
        //val rawContent = response.bodyAsText()
        //println(rawContent)
        val apiResponse: GetMyShips200Response = response.body()
        Ships.getShips(apiResponse)
        for (remainingCommand in remainingCommands) {
            println(" > $remainingCommand")
        }
        println("End of ships command.")
    }
}