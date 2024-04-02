package kr.j_jun.jlog.Service

import kr.j_jun.jlog.DTO.Post
import kr.j_jun.jlog.DTO.Response
import kr.j_jun.jlog.Entity.Posts
import kr.j_jun.jlog.Enum.ResponseStatus
import kr.j_jun.jlog.Repository.PostsRepository
import kr.j_jun.jlog.Util.CommonUtil.Companion.toMap
import kr.j_jun.jlog.Util.CommonUtil.Companion.toPage
import org.springframework.data.domain.PageRequest
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
class PostService(
    private val postsRepo: PostsRepository,
) {

    fun getMyPosts(user: UserDetails, page: Int?): Response {
        val posts = postsRepo.getAllByUserId(user.username, PageRequest.of(page.toPage(), 10)).map {
            Post.toItem(it)
        }

        return Response(ResponseStatus.OK, posts.toMap())
    }

    fun newPost(user: UserDetails, post: Post.Post): Response {
        val id = postsRepo.save(Posts(
            userId = user.username,
            title = post.title,
            content = post.content
        )).id

        return Response(ResponseStatus.OK, id)
    }

}