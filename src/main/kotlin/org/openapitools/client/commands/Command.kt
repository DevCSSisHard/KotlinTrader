package org.openapitools.client.commands

sealed interface Command {
    val commandName: String

    suspend fun processCommand(remainingCommands: List<String>)
}