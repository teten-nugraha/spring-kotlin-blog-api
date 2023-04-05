package id.ten.springkotlinblogapi.models

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "kategories")
data class Kategori (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? =null,

    @Column(unique = true)
    val name: String,

    val createdAt: LocalDateTime? = LocalDateTime.now(),
    val updatedAt: LocalDateTime? = LocalDateTime.now(),

)