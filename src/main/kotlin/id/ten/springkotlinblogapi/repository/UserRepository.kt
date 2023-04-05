package id.ten.springkotlinblogapi.repository

import id.ten.springkotlinblogapi.models.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface UserRepository: JpaRepository<User, Long>{

    fun findByUsername(username: String?): Optional<User>
    fun existsByUsername(username: String): Boolean
}