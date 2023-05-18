package org.openapitools.client.apis

import org.openapitools.client.infrastructure.NameConverter
import org.openapitools.client.models.ContractDeliverGood
import org.openapitools.client.models.GetContracts200Response
import java.text.NumberFormat
import java.util.*

/**
 *
 * Displays Information about a contract.
 *
 */

class Contracts {
    companion object {
        fun getContracts(apiResponse: GetContracts200Response) {
            val numFormat = NumberFormat.getNumberInstance(Locale.US)

            println("Contract ID: " + apiResponse.data[0].id)
            println("Faction: "+apiResponse.data[0].factionSymbol)
            println("***** Contract Terms below *****")
            println("Deliever "+ apiResponse.data[0].terms.deliver!![0].unitsRequired +" Units of: "  + NameConverter.convertToReadable(apiResponse.data[0].terms.deliver!![0].tradeSymbol))
            //println(apiResponse.data[0].terms.deliver!![0].unitsRequired)
            println("To Destination: "+apiResponse.data[0].terms.deliver!![0].destinationSymbol)
            println("Current Amount Held: "+apiResponse.data[0].terms.deliver!![0].unitsFulfilled)
            println("***** Payment information below *****")
            println("Payment upfront: "+ numFormat.format(apiResponse.data[0].terms.payment.onAccepted) + " Credits")
            println("Payment on fulfillment: "+ numFormat.format(apiResponse.data[0].terms.payment.onFulfilled) + " Credits")
        }
    }

}
