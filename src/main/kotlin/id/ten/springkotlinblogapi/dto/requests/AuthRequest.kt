package id.ten.springkotlinblogapi.dto.requests

import javax.validation.constraints.Size
import kotlin.math.min

data class AuthRequest(
    @field:Size(min=3, max=20)
    val username: String,

    @field:Size(min=8, max=40)
    val password: String,
)