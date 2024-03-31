package kr.j_jun.jlog.Entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.Date

@Document(collation = "users")
class Users(
    @Id
    val userId: String,
    var password: String,
    var nickname: String,
    var createdDatetime: Date = Date()
) {

}