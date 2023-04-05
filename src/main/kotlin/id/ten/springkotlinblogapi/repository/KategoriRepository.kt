package id.ten.springkotlinblogapi.repository

import id.ten.springkotlinblogapi.models.Kategori
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface KategoriRepository: JpaRepository<Kategori, Long> {

    fun findByName(name: String): Optional<Kategori>
    fun existsByName(name: String): Boolean

}