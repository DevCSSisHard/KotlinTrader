package org.openapitools.client.apis

import org.openapitools.client.models.GetMarket200Response
import org.openapitools.client.models.SellCargo201Response
import java.util.*

class Markets {
    companion object {
        fun viewMarkets(apiResponse: GetMarket200Response) {
            println("Market exports: ")
            for (item in apiResponse.data.exports){
                println(item.name + " - " + item.description)
            }
            println("Market Imports: ")
            for (item in apiResponse.data.imports){
                println(item.name + " - " + item.description)
            }
            println("Market Exchange: ")
            for (item in apiResponse.data.exchange){
                println(item.name + " - " + item.description)
            }

        }

        fun viewTransactions(apiResponse: GetMarket200Response) {
            println("Market Transactions: ")
            for (item in apiResponse.data.transactions!!){
                // Jesus Christ.
                println("Transaction: "+ item.units + " units of " + item.tradeSymbol.lowercase().capitalize()
                        + ". Transaction type: "+ item.type.name.lowercase().capitalize() + ", at " + item.pricePerUnit +" units each, for a total of "+ item.totalPrice + " units.")
            }
        }
    }

}
