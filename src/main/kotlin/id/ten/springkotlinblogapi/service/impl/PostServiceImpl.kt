package id.ten.springkotlinblogapi.service.impl

import id.ten.springkotlinblogapi.dto.requests.PostRequest
import id.ten.springkotlinblogapi.dto.responses.PostResponse
import id.ten.springkotlinblogapi.dto.responses.toResponse
import id.ten.springkotlinblogapi.exceptions.BadRequestException
import id.ten.springkotlinblogapi.models.Post
import id.ten.springkotlinblogapi.repository.KategoriRepository
import id.ten.springkotlinblogapi.repository.PostRepository
import id.ten.springkotlinblogapi.repository.UserRepository
import id.ten.springkotlinblogapi.service.PostService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
class PostServiceImpl: PostService {

    @Autowired
    private lateinit var postRepository: PostRepository

    @Autowired
    private lateinit var kategoriRepository: KategoriRepository

    @Autowired
    private lateinit var userRepository: UserRepository

    override fun addPost(postRequest: PostRequest): ResponseEntity<PostResponse> {
        if(postRepository.existsByTitle(postRequest.title)){
            throw BadRequestException("Title already taken")
        }

        if(!kategoriRepository.existsById(postRequest.kategoriId)) {
            throw BadRequestException("Kategori not found")
        }

        val kategori = kategoriRepository.findById(postRequest.kategoriId).get()
        val userDetails: UserDetails = SecurityContextHolder.getContext().authentication.principal as UserDetails
        val user = userRepository.findByUsername(userDetails.username).get()

        val post = Post(
            title = postRequest.title,
            content = postRequest.content,
            isActive = true,
            kategori = kategori,
            user = user,
        )

        return ResponseEntity.ok()
            .body(postRepository.save(post).toResponse())
    }

    override fun updatePost(id: Long, postRequest: PostRequest): ResponseEntity<PostResponse> {
        if(!postRepository.existsById(id)) {
            throw BadRequestException("Post Not Found")
        }

        if(!kategoriRepository.existsById(postRequest.kategoriId)) {
            throw BadRequestException("Kategori not found")
        }

        val kategori = kategoriRepository.findById(postRequest.kategoriId).get()
        val userDetails: UserDetails = SecurityContextHolder.getContext().authentication.principal as UserDetails
        val user = userRepository.findByUsername(userDetails.username).get()
        val post = postRepository.findById(id).get()
        val updatedPost: Post = post.copy(
            title = postRequest.title,
            content = postRequest.content,
            isActive = true,
            kategori = kategori,
            user = user,
        )
        return ResponseEntity.ok()
            .body(postRepository.save(updatedPost).toResponse())
    }

    override fun getPostById(id: Long): ResponseEntity<PostResponse> {
        val post = postRepository.findById(id).orElseThrow{ BadRequestException("Error: Post not found")}
        return ResponseEntity.ok()
            .body(post.toResponse())
    }

    override fun deletePostById(id: Long) {
        postRepository.deleteById(id)
    }

    override fun getAll(): ResponseEntity<List<PostResponse>> {
        val posts = postRepository.findAll()
        return ResponseEntity.ok(
            posts.map { it.toResponse() }
        )
    }

    override fun findByTitle(title: String): ResponseEntity<PostResponse> {
        if(!postRepository.existsByTitle(title)){
            throw BadRequestException("Title not found")
        }

        val post = postRepository.findByTitle(title).get()
        return ResponseEntity.ok()
            .body(post.toResponse())
    }
}