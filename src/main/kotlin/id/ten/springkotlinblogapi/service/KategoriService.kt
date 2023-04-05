package id.ten.springkotlinblogapi.service

import id.ten.springkotlinblogapi.dto.requests.KategoriRequest
import org.springframework.http.ResponseEntity

interface KategoriService {

    fun addKategori(kategori: KategoriRequest): ResponseEntity<Any>
    fun updateKategori(id: Long, kategori: KategoriRequest): ResponseEntity<Any>
    fun getKategoriById(id: Long): ResponseEntity<Any>
    fun deleteKategoriById(id: Long)
    fun getAll(): ResponseEntity<Any>

}