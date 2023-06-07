package org.openapitools.client.commands

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import org.openapitools.client.apis.Ships
import org.openapitools.client.infrastructure.NameConverter
import org.openapitools.client.models.*
import token

class Sell : Command{
    override val commandName: String = "Sell"

    override suspend fun processCommand(remainingCommands: List<String>) {
        //TODO: Move this.
        displayShipList(HttpClientObject.client)
        println("Select a ship: ")
        val shipToSellFromRaw = readLine()!!
        val shipToSellFrom = selectAShip(HttpClientObject.client, shipToSellFromRaw)
        if(shipToSellFrom == null) {
            println("Error viewing ship to sell from. Try again.")
            return
        }
        println("Ship info and cargo: ")
        Ships.getShipCargo(shipToSellFrom)
        println("Item to sell: ")
        val goodToSellString = readLine()!!
        println("Amount to sell: ")
        val amountToSellString = readLine()!!
        val amountToSell = amountToSellString.toInt()
        val goodToSell = NameConverter.convertToInternal(goodToSellString)
        println(goodToSell + " "+ amountToSellString)
        val response: HttpResponse = HttpClientObject.client.post("https://api.spacetraders.io/v2/my/ships/${shipToSellFrom.symbol}/sell") {
            bearerAuth(token)
            contentType(ContentType.Application.Json)
            setBody(SellCargoRequest(goodToSell,amountToSell))
        }
        //val rawContent:String = response.bodyAsText()
        //println(rawContent)
        val apiResponse: SellCargo201Response = response.body()
        //println(response.status)
        println("Sold "+ apiResponse.data.transaction.units + " units of "+ apiResponse.data.transaction.tradeSymbol + " for a total of " + apiResponse.data.transaction.totalPrice)

    }

    private suspend fun selectAShip(client: HttpClient, shipToSellFromRaw: String): Ship {
        val response: HttpResponse = HttpClientObject.client.get("https://api.spacetraders.io/v2/my/ships/${shipToSellFromRaw}") {
            bearerAuth(token)
            contentType(ContentType.Application.Json)
        }
        val apiResponse: GetMyShip200Response = response.body()
        return apiResponse.data
    }

    private suspend fun displayShipList(client: HttpClient) {
        val response: HttpResponse = HttpClientObject.client.get("https://api.spacetraders.io/v2/my/ships") {
            bearerAuth(token)
            contentType(ContentType.Application.Json)
        }

        val rawContent = response.bodyAsText()
        println(rawContent)
        val apiResponse: GetMyShips200Response = response.body()
        Ships.getShips(apiResponse)
    }
}