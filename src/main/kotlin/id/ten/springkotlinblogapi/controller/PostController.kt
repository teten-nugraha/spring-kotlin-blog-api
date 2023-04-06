package id.ten.springkotlinblogapi.controller

import id.ten.springkotlinblogapi.dto.requests.PostRequest
import id.ten.springkotlinblogapi.dto.responses.PostResponse
import id.ten.springkotlinblogapi.service.PostService
import id.ten.springkotlinblogapi.util.Constant.Companion.POST_PATH
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
@RequestMapping(POST_PATH)
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
class PostController {

    @Autowired
    private lateinit var postService: PostService

    @PostMapping
    fun createPost(@Valid @RequestBody postRequest: PostRequest): ResponseEntity<PostResponse>{
        return postService.addPost(postRequest);
    }

    @GetMapping
    fun getAllPosts(): ResponseEntity<List<PostResponse>> {
        return postService.getAll()
    }

    @GetMapping("/post/{id}")
    fun getPostById(@PathVariable("id") postId: Long): ResponseEntity<PostResponse> {
        return postService.getPostById(postId)
    }

    @PutMapping("/{id}")
    fun updatePost(
        @PathVariable("id") postId: Long,
        @Valid @RequestBody postRequest: PostRequest
    ): ResponseEntity<PostResponse>{
        return postService.updatePost(postId, postRequest)
    }

    @DeleteMapping("/{id}")
    fun deletePost(
        @PathVariable("id") postId: Long
    ): ResponseEntity<Any>{
        postService.deletePostById(postId)
        return ResponseEntity.ok()
            .body("Deleted")
    }

    @GetMapping("/findByTitle/{title}")
    fun findPostByTitle(
        @PathVariable("title") title: String
    ): ResponseEntity<PostResponse> {
        return postService.findByTitle(title)
    }

}