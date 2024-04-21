package kr.j_jun.jlog.Controller

import kr.j_jun.jlog.DTO.Post
import kr.j_jun.jlog.DTO.Response
import kr.j_jun.jlog.Service.PostService
import org.springframework.core.io.Resource
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/posts")
class PostController(private val service: PostService) {

    @GetMapping("/my")
    fun getMyPosts(
        @SessionAttribute(name = "userId", required = true) userId: String,
        @RequestParam("p") page: Int?,
    ): Response {
        return service.getMyPosts(userId, page)
    }

    @GetMapping("/{id}")
    fun getPost(
        @PathVariable("id") postId: String,
    ): Response {
        return service.getPost(postId)
    }

    @GetMapping("/download")
    fun download(
        @RequestParam("f") fileName: String,
    ): ResponseEntity<Resource> {
        return service.download(fileName)
    }

    @PostMapping
    fun newPost(
        @SessionAttribute(name = "userId", required = true) userId: String,
        @RequestBody post: Post.Post
    ): Response {
        return service.newPost(userId, post)
    }

    @PostMapping("/file")
    fun attachFile(
        @SessionAttribute(name = "userId", required = true) userId: String,
        @RequestPart("file") file: List<MultipartFile>
    ): Response {
        return service.attachFile(userId, file)
    }

    @DeleteMapping("/{id}/file")
    fun deleteFile(
        @SessionAttribute(name = "userId", required = true) userId: String,
        @PathVariable("id") postId: String,
        @RequestBody files: List<String>
    ): Response {
        return service.deleteFile(userId, postId, files)
    }

    @PatchMapping("/{id}")
    fun updatePost(
        @SessionAttribute(name = "userId", required = true) userId: String,
        @PathVariable("id") postId: String,
        @RequestBody data: Post.Update
    ): Response {
        return service.updatePost(userId, postId, data)
    }
}