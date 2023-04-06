package id.ten.springkotlinblogapi.repository

import id.ten.springkotlinblogapi.models.Post
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface PostRepository: JpaRepository<Post, Long> {

    fun existsByTitle(title: String): Boolean
    fun findByTitle(title: String): Optional<Post>

}