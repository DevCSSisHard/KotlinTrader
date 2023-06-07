package org.openapitools.client.commands

import CurrentData
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import org.openapitools.client.apis.Ships
import org.openapitools.client.models.GetShipyard200Response
import token

class View: Command {
    override val commandName: String= "View"

    override suspend fun processCommand(remainingCommands: List<String>) {
        //View Ships [Shipyard]
        //Remaing should be Ships [shipyard]
        if(remainingCommands[0] == "Ships") {
            val location = Ships.getLocationSystem(CurrentData.selectedShip!!)
            val locationToCheck = remainingCommands[1]
            displayShipyardShips(HttpClientObject.client, location, locationToCheck)
        }
    }

    private suspend fun displayShipyardShips(client: HttpClient, location: String, locationToCheck: String) {
        val response: HttpResponse = HttpClientObject.client.get("https://api.spacetraders.io/v2/systems/${location}/waypoints/${locationToCheck}/shipyard") {
            bearerAuth(token)
            contentType(ContentType.Application.Json)
        }
        val apiResponse: GetShipyard200Response = response.body()
        println(apiResponse.data.shipTypes)
    }
}