package id.ten.springkotlinblogapi.service.impl

import id.ten.springkotlinblogapi.dto.requests.KategoriRequest
import id.ten.springkotlinblogapi.dto.responses.MessageResponse
import id.ten.springkotlinblogapi.exceptions.BadRequestException
import id.ten.springkotlinblogapi.exceptions.ResourceNotFoundException
import id.ten.springkotlinblogapi.models.Kategori
import id.ten.springkotlinblogapi.repository.KategoriRepository
import id.ten.springkotlinblogapi.service.KategoriService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class KategoriServiceImpl(
    private val kategoriRepository: KategoriRepository
): KategoriService {

    override fun addKategori(kategoriRequest: KategoriRequest): ResponseEntity<Any> {

        if(kategoriRepository.existsByName(kategoriRequest.name)){
            throw BadRequestException("Kategori name already taken")
        }

        val kategori = Kategori(
            name = kategoriRequest.name
        )
        return ResponseEntity.ok().body(kategoriRepository.save(kategori))
    }

    override fun updateKategori(id: Long, kategoriRequest: KategoriRequest): ResponseEntity<Any> {

        val kategori = kategoriRepository.findById(id).orElseThrow{ ResourceNotFoundException("Error: kategori not found")}
        val updatedKategori = kategori.copy(
            name = kategoriRequest.name
        )

        return ResponseEntity.ok()
            .body(kategoriRepository.save(updatedKategori))

    }

    override fun getKategoriById(id: Long): ResponseEntity<Any> {
        val kategori = kategoriRepository.findById(id).orElseThrow{ ResourceNotFoundException("Error: kategori not found")}
        return ResponseEntity.ok()
            .body(kategori)
    }

    override fun deleteKategoriById(id: Long) {
        kategoriRepository.findById(id)
            .map { kategoriRepository.deleteById(id) }
            .orElseThrow{ ResourceNotFoundException("Error: kategori not found")}
    }

    override fun getAll(): ResponseEntity<Any> {
        return ResponseEntity.ok()
            .body(kategoriRepository.findAll())
    }


}