package kr.j_jun.jlog.DTO

class User {

    data class Register(
        val id: String,
        val password: String,
        val nickname: String
    )
}