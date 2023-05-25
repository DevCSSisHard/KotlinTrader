package org.openapitools.client.apis

import org.openapitools.client.infrastructure.NameConverter.Companion.convertToReadable
import org.openapitools.client.models.GetSystemWaypoints200Response
import org.openapitools.client.models.Waypoint
import org.openapitools.client.models.WaypointTrait
import org.openapitools.client.models.WaypointType

/** Grab and format all waypoints in system and output their symbol, name, and description.
 *
 *
 */

class Waypoints {
    companion object {
        fun getWaypoints(apiResponse: GetSystemWaypoints200Response) {
            for ( item in apiResponse.data)  {
                if(item.type == WaypointType.jUMPGATE) {
                    println("\n** Jump Gate ** : " + item.symbol)
                    continue
                }
                println("\n***** Waypoint Information *****\nUnique Identifier: " + item.symbol +"\nName: "+ item.traits[0].name + " - " + item.traits[0].description)
                println("System " + item.systemSymbol + " Waypoint "+ item.symbol + " is a " + convertToReadable(item.type.toString()))

            }
        }
        fun getShipyards(apiResponse: GetSystemWaypoints200Response): ArrayList<String> {
            val shipyardArr = ArrayList<String>()
            for ( item in apiResponse.data)  {
                //Useless check, but I am leaving it.
                if(item.type == WaypointType.jUMPGATE) {
                    continue
                } else {
                    for (element in item.traits) {
                        if (element.symbol.value.lowercase() == "shipyard".lowercase()) {
                            shipyardArr.add(item.symbol)
                        }
                    }
                }
            }
            return shipyardArr
        }
        fun printShipYards(apiResponse: GetSystemWaypoints200Response) {
            val shipyardList = getShipyards(apiResponse)
            println("***** Shipyards *****")
            println(shipyardList.joinToString(" "))
        }
        fun viewShipYards(apiResponse: GetSystemWaypoints200Response) {

        }
    }
}

