package org.openapitools.client.infrastructure

import java.util.*

class NameConverter(private val input: String) {
    companion object {
        fun convertToReadable(tradeSymbol: String): Any? {
            val words = tradeSymbol.split("_").map {
                it.lowercase()
                    .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
            }
            return words.joinToString(" ")
        }

        fun convertToInternal(goodToSell: String): String {
            return goodToSell.uppercase().replace(" ", "_")
        }
    }
}
