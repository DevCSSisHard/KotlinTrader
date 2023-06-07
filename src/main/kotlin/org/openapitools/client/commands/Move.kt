package org.openapitools.client.commands

class Move : Command {
    override val commandName: String = "Move"

    override suspend fun processCommand(remainingCommands: List<String>) {
        println("Place Holder stuff.")
    }
}