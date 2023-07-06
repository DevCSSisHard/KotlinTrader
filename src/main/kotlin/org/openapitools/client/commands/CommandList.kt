package org.openapitools.client.commands

class CommandList: Command {
    override val commandName: String = "Commands"

    override suspend fun processCommand(remainingCommands: List<String>) {
        //Print a list of all commands with syntax.
        print("Contracts - Display available contracts\n")
        print("Default - Select a default ship\n")
        print("Dock - Dock selected ship at location it is orbiting. MUST HAVE SHIP SELECTED PRIOR.\n")
        print("Extract - Selected ship will attempt to extract minerals at its location. MUST HAVE SHIP SELECTED PRIOR.\n")
        print("Info - Display Agent Information.\n")
        print("Market - Display Market Information. Changes if selected ship is docked or not. MUST HAVE SHIP SELECTED PRIOR.\n")
        print("Move [Ship] [Location] - Moves a ship to a location. Can NOT be docked.\n")
        print("Orbit - Undock or Enter orbit at location. MUST HAVE SHIP SELECTED PRIOR.\n")
        print("Purchase [Type of Purchase] [Ship Type] - *WIP* Currently only 'ship' is valid, eventually market items will be added.\n Current example; Purchase ship mining drone\n")
        print("Refuel - Refuel selected ship. MUST HAVE SHIP SELECTED PRIOR.\n")
        print("Report [Scale] [Type] - Generate a status report based on paramters of either fleet or ship and full or not.\n")
        print("Select [Ship] - Select a ship to replace default.\n")
        print("Sell - Sell item(s) from a ship. Ship should be docked.\n")
        print("Ships - Display quick list with information on ships in fleet.\n")
        print("Shipyards - Show shipyards in the vicinity to reference ship. MUST HAVE SHIP SELECTED PRIOR.\n")
        print("Undock - Enter orbit around location. MUST HAVE SHIP SELECTED PRIOR.\n")
        print("View [Type] - Broken Probably.\n")
        print("Waypoints - Display all waypoints and type of waypoints relative to ship selected. MUST HAVE SHIP SELECTED PRIOR.\n")
    }
}