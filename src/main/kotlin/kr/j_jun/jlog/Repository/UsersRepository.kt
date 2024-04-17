package kr.j_jun.jlog.Repository

import kr.j_jun.jlog.Entity.Users
import org.springframework.data.mongodb.repository.MongoRepository


interface UsersRepository: MongoRepository<Users, String> {

}