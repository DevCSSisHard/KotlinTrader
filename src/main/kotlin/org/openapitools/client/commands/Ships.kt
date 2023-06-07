package org.openapitools.client.commands

class Ships : Command{
    override val commandName: String = "Ships"

    override suspend fun processCommand(remainingCommands: List<String>) {
        println("Place Holder stuff but inside Ships Command: \n")
        for (remainingCommand in remainingCommands) {
            println(" > $remainingCommand")
        }
    }
}