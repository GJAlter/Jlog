package kr.j_jun.jlog.DTO

class User {

    data class Register(
        val id: String,
        val password: String,
        val nickname: String,
        val notion: String
    )

    data class Login(
        val userId: String,
        val password: String,
    )
}