package id.ten.springkotlinblogapi.service

import id.ten.springkotlinblogapi.dto.requests.KategoriRequest
import id.ten.springkotlinblogapi.dto.requests.PostRequest
import id.ten.springkotlinblogapi.dto.responses.PostResponse
import org.springframework.http.ResponseEntity

interface PostService {

    fun addPost(postRequest: PostRequest): ResponseEntity<PostResponse>
    fun updatePost(id: Long, postRequest: PostRequest): ResponseEntity<PostResponse>
    fun getPostById(id: Long): ResponseEntity<PostResponse>
    fun deletePostById(id: Long)
    fun getAll(): ResponseEntity<List<PostResponse>>
    fun findByTitle(title: String): ResponseEntity<PostResponse>

}