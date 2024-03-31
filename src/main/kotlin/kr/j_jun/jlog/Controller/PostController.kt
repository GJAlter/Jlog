package kr.j_jun.jlog.Controller

import kr.j_jun.jlog.DTO.Post
import kr.j_jun.jlog.DTO.Response
import kr.j_jun.jlog.Service.PostService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/posts")
class PostController(private val service: PostService) {

    @PostMapping
    fun newPost(
        @AuthenticationPrincipal user: UserDetails,
        @RequestBody post: Post.Post
    ): Response {
        return service.newPost(user, post)
    }
}