package kr.j_jun.jlog.Controller

import kr.j_jun.jlog.DTO.Post
import kr.j_jun.jlog.DTO.Response
import kr.j_jun.jlog.Service.PostService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/posts")
class PostController(private val service: PostService) {

    @GetMapping("/my")
    fun getMyPosts(
        @AuthenticationPrincipal user: UserDetails,
        @RequestParam("p") page: Int?,
    ): Response {
        return service.getMyPosts(user, page)
    }

    @GetMapping("/{id}")
    fun getPost(
        @PathVariable("id") postId: String,
    ): Response {
        return service.getPost(postId)
    }

    @PostMapping
    fun newPost(
        @AuthenticationPrincipal user: UserDetails,
        @RequestBody post: Post.Post
    ): Response {
        return service.newPost(user, post)
    }

    @PostMapping("/file")
    fun attachFile(
        @AuthenticationPrincipal user: UserDetails,
        @RequestPart("file") file: List<MultipartFile>
    ): Response {
       return service.attachFile(user, file)
    }

    @PatchMapping("/{id}")
    fun updatePost(
        @AuthenticationPrincipal user: UserDetails,
        @PathVariable("id") postId: String,
        @RequestBody data: Post.Update
    ): Response {
        return service.updatePost(user, postId, data)
    }
}