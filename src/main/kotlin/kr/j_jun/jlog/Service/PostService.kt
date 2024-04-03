package kr.j_jun.jlog.Service

import kr.j_jun.jlog.DTO.Post
import kr.j_jun.jlog.DTO.Response
import kr.j_jun.jlog.Entity.Posts
import kr.j_jun.jlog.Enum.ResponseStatus
import kr.j_jun.jlog.Exceptions
import kr.j_jun.jlog.Repository.PostsRepository
import kr.j_jun.jlog.Util.CommonUtil.Companion.toMap
import kr.j_jun.jlog.Util.CommonUtil.Companion.toPage
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.PageRequest
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Paths
import java.util.*
import kotlin.jvm.optionals.getOrNull

@Service
class PostService(
    private val postsRepo: PostsRepository,
) {

    @Value("\${kr.j_jun.jlog.value.attach-upload-path}")
    lateinit var uploadPath: String

    fun getMyPosts(user: UserDetails, page: Int?): Response {
        val posts = postsRepo.getAllByUserId(user.username, PageRequest.of(page.toPage(), 10)).map {
            Post.toItem(it)
        }

        return Response(ResponseStatus.OK, posts.toMap())
    }

    fun getPost(id: String): Response {
        val post = postsRepo.findById(id).getOrNull()?: throw Exceptions.DataNotFoundException("게시글을 찾을 수 없습니다.")

        return Response(ResponseStatus.OK, Post.toDetail(post))
    }

    fun newPost(user: UserDetails, post: Post.Post): Response {
        val id = postsRepo.save(Posts(
            userId = user.username,
            title = post.title,
            content = post.content,
            attaches = post.attaches
        )).id

        return Response(ResponseStatus.OK, id)
    }

    fun attachFile(user: UserDetails, files: List<MultipartFile>): Response {
        val newFileNames = ArrayList<String>()
        files.forEach { file ->
            var originalFileName = file.originalFilename
            if(originalFileName != null) {
               originalFileName = originalFileName.substring(originalFileName.lastIndexOf("\\") + 1)
            }
            val newFileName = "${randomName()}_$originalFileName"
            val newFilePath = "$uploadPath${File.separator}$newFileName"

            file.transferTo(Paths.get(newFilePath))
            newFileNames.add(newFileName)
        }

        return Response(ResponseStatus.OK, newFileNames)
    }

    fun updatePost(user: UserDetails, postId: String, data: Post.Update): Response {
        val post = postsRepo.getByIdAndUserId(postId, user.username)?: throw Exceptions.DataNotFoundException("게시글을 찾을 수 없습니다.")
        post.apply {
            title = data.title
            content = data.content
            if(attaches != null) {
                attaches!!.forEach {
                    val isDeleted = if(data.attaches != null) {
                        data.attaches!!.indexOf(it) < 1
                    } else true
                    if(isDeleted) {
                        val deleteFile = File("$uploadPath${File.separator}$it")
                        if(deleteFile.exists()) {
                            deleteFile.delete()
                        }
                    }
                }
            }
            attaches = data.attaches
            modifiedDatetime = Date()
        }

        postsRepo.save(post)

        return Response(ResponseStatus.OK)
    }

    private fun randomName(): String {
        return "${Date().time}-${UUID.randomUUID().toString().substring(0, 8)}"
    }

}