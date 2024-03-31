package kr.j_jun.jlog.Repository

import kr.j_jun.jlog.Entity.Post
import org.springframework.data.mongodb.repository.MongoRepository

interface PostRepository: MongoRepository<Post, Int> {
}