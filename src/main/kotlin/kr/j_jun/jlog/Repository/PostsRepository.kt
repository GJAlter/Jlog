package kr.j_jun.jlog.Repository

import kr.j_jun.jlog.Entity.Posts
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.mongodb.repository.MongoRepository

interface PostsRepository: MongoRepository<Posts, String> {

    fun getByIdAndUserId(id: String, userId: String): Posts?
    fun getAllByUserId(userId: String, pageRequest: PageRequest): Page<Posts>
    fun getAllByUserIdOrderByModifiedDatetimeDesc(userId: String, pageRequest: PageRequest): Page<Posts>
}