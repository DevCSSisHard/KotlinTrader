package org.openapitools.client.apis

import org.openapitools.client.infrastructure.NameConverter.Companion.convertToReadable
import org.openapitools.client.models.GetMyShips200Response
import org.openapitools.client.models.Ship

class Ships {
    companion object {
        fun getShips(apiResponse: GetMyShips200Response) {
            for (item in apiResponse.data) {
                println("Ship Symbol: "+ getName(item))
                println("Ship Type:" + getType(item))
                println("Ship Role: "+item.registration.role)
                println("Ship Faction: "+item.registration.factionSymbol)
                println("Location of Ship: " + getLocation(item))
                println("Ship Status: "+ getStatus(item))
            }
        }
        fun shipReport(ship:Ship){
            println("******** Ship Report ********")
            println("Ship Name: "+getName(ship))
            println("Ship Location: "+getLocation(ship))
            println("Ship Status: "+getStatus(ship))
            println("Ship Type: "+getType(ship))
            getFuel(ship)
            println(getCrew(ship))
            getCrewInfo(ship)
            getShipInfo(ship)
            getShipComponents(ship)
            getShipModules(ship)
            println("\n")
            getShipCargo(ship)
        }

        fun getLocation(ship: Ship): String {
            return ship.nav.waypointSymbol
        }
        fun getLocationSystem(ship: Ship): String {
            return ship.nav.systemSymbol
        }
        fun getStatus(ship: Ship): String {
            return ship.nav.status.toString()
        }
        // Guess I need toString twice?
        fun getType(ship: Ship): String {
            return convertToReadable(ship.frame.symbol.toString()).toString()
        }
        fun getName(ship: Ship): String {
            return ship.symbol
        }
        fun getCrew(ship: Ship): String{
            return "${ship.symbol} has a crew of ${ship.crew.current}, with a capacity of ${ship.crew.capacity}. ${ship.crew.required} crew members are needed to run the ship."
        }
        fun getCrewInfo(ship: Ship) {
            println("**** Crew info for ${ship.symbol} ****")
            println("${ship.symbol} has a crew of ${ship.crew.current}, with a capacity of ${ship.crew.capacity}.")
            println("Crewmen earn ${ship.crew.wages} and the crews morale is at ${ship.crew.morale}")
            println("The crew roation is "+ ship.crew.rotation.toString().uppercase())
        }
        fun getShipInfo(ship:Ship) {
            println("**** ${ship.symbol} Info ****")
            println("Class: " + ship.frame.symbol.toString().uppercase())
            println("Module capacity ${ship.frame.moduleSlots}. Mounting points ${ship.frame.mountingPoints}.")
            println("Fuel capacity ${ship.frame.fuelCapacity}")
            println("Ship requirements: Power ${ship.frame.requirements.power}, Crewmen ${ship.frame.requirements.crew}" +
                    " Slots ${ship.frame.requirements.slots}")
            println("Ship overall condition: ${ship.frame.condition}")
        }
        fun getShipComponents(ship:Ship) {
            println("**** ${ship.symbol} Components ****")
            println("Reactor: ${ship.reactor.name}. Power output: ${ship.reactor.powerOutput} Condition: ${ship.reactor.condition}")
            println("Engine: ${ship.engine.name}. Speed: ${ship.engine.speed} Condition: ${ship.engine.condition}")
            println("** Modules **")
            for (part in ship.modules) {
                println("${part.name}: ${part.description}")
                println("Power consumption: ${part.requirements.power} Slot usage: ${part.requirements.slots}")
                if(part.range != null) {
                    println("Range: ${part.range}")
                }
            }
        }
        fun getShipModules(ship:Ship){
            println("**** ${ship.symbol} Mounts ****")
            for(mount in ship.mounts){
                println("${mount.name}, ${mount.description}")
            }
        }
        fun getShipCargo(ship:Ship){
            println("**** ${ship.symbol} Cargo Contents ****")
            println("Capacity: ${ship.cargo.capacity}. Units: ${ship.cargo.units}")
            for (item in ship.cargo.inventory){
                println("${item.units} - ${item.name}: ${item.description}")
            }
        }
        fun getFuel(ship:Ship){
            println("**** ${ship.symbol} Fuel Information ****")
            println("Current Fuel: ${ship.fuel.current}. Fuel Capacity: ${ship.fuel.capacity}")
            println("Fuel consumed: ${ship.fuel.consumed?.amount}")
        }
    }

}
