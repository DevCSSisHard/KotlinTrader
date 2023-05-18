import kotlinx.serialization.Serializable

@Serializable
data class ApiResult(
    val data: RegistrationResult,
)