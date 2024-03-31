package kr.j_jun.jlog.Repository

import kr.j_jun.jlog.Entity.Posts
import org.springframework.data.mongodb.repository.MongoRepository

interface PostsRepository: MongoRepository<Posts, Int> {
}