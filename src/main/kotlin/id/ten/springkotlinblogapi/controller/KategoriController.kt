package id.ten.springkotlinblogapi.controller

import id.ten.springkotlinblogapi.dto.requests.KategoriRequest
import id.ten.springkotlinblogapi.service.KategoriService
import id.ten.springkotlinblogapi.util.Constant.Companion.KATEGORI_PATH
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping(KATEGORI_PATH)
@PreAuthorize("hasRole('ADMIN')")
class KategoriController {

    @Autowired
    private lateinit var kategoriService: KategoriService

    @GetMapping
    fun getAllKategories(): ResponseEntity<Any> {
        return kategoriService.getAll()
    }

    @PostMapping
    fun createKategori(@Valid @RequestBody kategoriRequest: KategoriRequest): ResponseEntity<Any> {
        return kategoriService.addKategori(kategoriRequest)
    }

    @GetMapping("/{id}")
    fun getKategoryById(@PathVariable("id") id: Long): ResponseEntity<Any> {
        return kategoriService.getKategoriById(id)
    }

    @PutMapping("/{id}")
    fun updateKategori(@PathVariable("id") id: Long, @Valid @RequestBody kategoriRequest: KategoriRequest): ResponseEntity<Any> {
        return kategoriService.updateKategori(id, kategoriRequest)
    }

    @DeleteMapping("/{id}")
    fun deleteKategoryById(@PathVariable("id") id: Long): ResponseEntity<Any> {
        kategoriService.deleteKategoriById(id)
        return ResponseEntity.ok()
            .body("deleted");
    }
}