import kotlinx.serialization.Serializable

@Serializable
data class RegistrationData(
    val symbol: String,
    val faction: String,
)

@Serializable
data class RegistrationResult(
    val token: String,
    val agent: String,
    val contract: String,
    val faction: String,
    val ship: String,
)