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
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.net.URLEncoder
import java.nio.file.Paths
import java.util.*
import kotlin.jvm.optionals.getOrNull

@Service
class PostService(
    private val postsRepo: PostsRepository,
) {

    @Value("\${kr.j_jun.jlog.value.attach-upload-path}")
    lateinit var uploadPath: String

    fun getMyPosts(userId: String, page: Int?): Response {
        val posts = postsRepo.getAllByUserId(userId, PageRequest.of(page.toPage(), 10)).map {
            Post.toItem(it)
        }

        return Response(ResponseStatus.OK, posts.toMap())
    }

    fun getPost(id: String): Response {
        val post = postsRepo.findById(id).getOrNull()?: throw Exceptions.DataNotFoundException("게시글을 찾을 수 없습니다.")

        return Response(ResponseStatus.OK, Post.toDetail(post))
    }

    fun download(fileName: String): ResponseEntity<Resource> {
        val file = File("$uploadPath${File.separator}$fileName")
        val urlEncoded = URLEncoder.encode(fileName.split("_", limit = 2)[1], "UTF-8")

        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=$urlEncoded")
            .header(HttpHeaders.CONTENT_LENGTH, file.length().toString())
            .body(UrlResource(file.toURI()))
    }

    fun newPost(userId: String, post: Post.Post): Response {
        val id = postsRepo.save(Posts(
            userId = userId,
            title = post.title,
            content = post.content,
            attaches = post.attaches
        )).id

        return Response(ResponseStatus.OK, id)
    }

    fun attachFile(userId: String, files: List<MultipartFile>): Response {
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

    fun updatePost(userId: String, postId: String, data: Post.Update): Response {
        val post = postsRepo.getByIdAndUserId(postId, userId)?: throw Exceptions.DataNotFoundException("게시글을 찾을 수 없습니다.")
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