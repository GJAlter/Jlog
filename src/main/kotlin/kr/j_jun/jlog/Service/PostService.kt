package kr.j_jun.jlog.Service

import kr.j_jun.jlog.DTO.Post
import kr.j_jun.jlog.DTO.Response
import kr.j_jun.jlog.Entity.Posts
import kr.j_jun.jlog.Enum.ResponseStatus
import kr.j_jun.jlog.Repository.PostsRepository
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
class PostService(
    private val postsRepo: PostsRepository,
) {

    fun newPost(user: UserDetails, post: Post.Post): Response {
        val id = postsRepo.save(Posts(
            userId = user.username,
            title = post.title,
            content = post.content
        )).id

        return Response(ResponseStatus.OK, id)
    }

}