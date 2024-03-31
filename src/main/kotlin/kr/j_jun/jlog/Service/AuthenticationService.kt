package kr.j_jun.jlog.Service

import kr.j_jun.jlog.DTO.Response
import kr.j_jun.jlog.DTO.User
import kr.j_jun.jlog.Entity.Users
import kr.j_jun.jlog.Enum.ResponseStatus
import kr.j_jun.jlog.Repository.UsersRepository
import org.springframework.stereotype.Service

@Service
class AuthenticationService(
    private val usersRepo: UsersRepository
) {

    fun register(user: User.Register): Response {
        usersRepo.save(Users(
            userId = user.id,
            password = user.password,
            nickname = user.nickname
        ))

        return Response(ResponseStatus.OK)
    }
}