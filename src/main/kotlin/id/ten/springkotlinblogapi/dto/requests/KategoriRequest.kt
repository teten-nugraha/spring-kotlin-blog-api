package id.ten.springkotlinblogapi.dto.requests

import javax.validation.constraints.Size

data class KategoriRequest (
    @field:Size(min=8, max = 30)
    val name: String
)