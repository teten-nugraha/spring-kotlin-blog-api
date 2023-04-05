package id.ten.springkotlinblogapi.dto.responses

data class LoginResponse(
    var token: String,
    val id: Long,
    val username: String,
    val isAdmin: Boolean
)