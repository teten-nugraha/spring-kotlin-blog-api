package id.ten.springkotlinblogapi.dto.responses

import id.ten.springkotlinblogapi.models.Post
import java.time.LocalDateTime

data class PostResponse (
    val id: Long,
    val title: String,
    val kategori: String,
    val writer: String,
    val content: String,
    val writeDate: LocalDateTime
)

fun Post.toResponse() = PostResponse (
    id = id,
    title = title,
    kategori = this.kategori.name,
    writer = this.user.username,
    content = content,
    writeDate = this.createdAt
)