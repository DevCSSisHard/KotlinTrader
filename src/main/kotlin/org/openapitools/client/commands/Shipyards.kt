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

class Shipyards: Command {
    override val commandName: String = "Shipyards"

    override suspend fun processCommand(remainingCommands: List<String>) {
        if(CurrentData.selectedShip == null){
            println("WARNING: No reference ship selected. Select a ship first.")
            return
        }
        val location = Ships.getLocationSystem(CurrentData.selectedShip!!)
        CurrentData.lastShipYard = getShipYards(HttpClientObject.client, location)
    }

    private suspend fun getShipYards(client: HttpClient, location: String): String {
        val response: HttpResponse = HttpClientObject.client.get("https://api.spacetraders.io/v2/systems/${location}/waypoints") {
            bearerAuth(token)
            contentType(ContentType.Application.Json)
        }
        val apiResponse: GetSystemWaypoints200Response = response.body()
        //Waypoints.printShipYards(apiResponse)
        return Waypoints.getShipYards(apiResponse)
    }
}