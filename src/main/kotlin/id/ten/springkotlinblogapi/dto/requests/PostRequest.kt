package id.ten.springkotlinblogapi.dto.requests

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class PostRequest (
    @field:Size(min=8, max = 30)
    @field:NotBlank
    val title: String,
    @field:NotBlank
    val content: String,
    @field:NotNull
    val kategoriId: Long
)