package org.openapitools.client.commands

class Register: Command {
    override val commandName: String = "Register"

    override suspend fun processCommand(remainingCommands: List<String>) {
        //Place holder if I do decide to implement a registration.
    }
}