package kr.j_jun.jlog.Entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.Date

@Document(collection = "users")
class Users(
    @Id
    val userId: String,
    var pw: String,
    var nickname: String,
    var createdDatetime: Date = Date()
)