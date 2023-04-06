package id.ten.springkotlinblogapi.models

import id.ten.springkotlinblogapi.dto.responses.PostResponse
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "posts")
data class Post(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(unique = true)
    val title: String,
    val content: String,
    val isActive: Boolean = true,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "kategori_id")
    var kategori: Kategori,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    var user: User,

    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
)