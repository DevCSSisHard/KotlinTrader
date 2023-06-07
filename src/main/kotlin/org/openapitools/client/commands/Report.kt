package org.openapitools.client.commands

import CurrentData
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import org.openapitools.client.apis.Ships
import org.openapitools.client.models.GetMyShips200Response
import token

/**
 * Syntax should be 'Report [Scale] [Type]
 * Scale: Fleet or Ship
 * Type: Full or Normal
 */
class Report: Command {
    override val commandName: String = "Report"

    override suspend fun processCommand(remainingCommands: List<String>) {
        if(remainingCommands[0] == "ship"){
            if(remainingCommands[1] == "full"){
                Ships.shipReport(CurrentData.selectedShip!!)
            }

        }else if(remainingCommands[0]=="Fleet"){

            if(remainingCommands[1]=="full"){
                val response: HttpResponse = HttpClientObject.client.get("https://api.spacetraders.io/v2/my/ships") {
                    bearerAuth(token)
                    contentType(ContentType.Application.Json)
                }
                val apiResponse: GetMyShips200Response = response.body()

                for (ship in apiResponse.data) {
                    Ships.shipReport(ship)
                }
            }

        }
    }
}