package org.openapitools.client.commands

import CurrentData
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import org.openapitools.client.apis.Ships
import org.openapitools.client.apis.Waypoints
import org.openapitools.client.models.GetSystemWaypoints200Response
import token

class Waypoints: Command {
    override val commandName: String = "Waypoints"

    override suspend fun processCommand(remainingCommands: List<String>) {
        val location = Ships.getLocationSystem(CurrentData.selectedShip!!)
        displayWaypoints(HttpClientObject.client, location)
    }

    private suspend fun displayWaypoints(client: HttpClient, location: String) {
        val response: HttpResponse = HttpClientObject.client.get("https://api.spacetraders.io/v2/systems/${location}/waypoints") {
            bearerAuth(token)
            contentType(ContentType.Application.Json)
        }
        val apiResponse: GetSystemWaypoints200Response = response.body()
        Waypoints.getWaypoints(apiResponse)
    }
}