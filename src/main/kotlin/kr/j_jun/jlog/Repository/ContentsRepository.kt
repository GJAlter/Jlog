package kr.j_jun.jlog.Repository

import kr.j_jun.jlog.Entity.Contents
import org.springframework.data.mongodb.repository.MongoRepository

interface ContentsRepository: MongoRepository<Contents, Int> {
}