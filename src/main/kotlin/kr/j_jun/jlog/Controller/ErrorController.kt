package kr.j_jun.jlog.Controller

import kr.j_jun.jlog.DTO.Response
import kr.j_jun.jlog.Enum.ResponseStatus
import kr.j_jun.jlog.Exceptions
import org.springframework.http.HttpEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.resource.NoResourceFoundException
import java.net.http.HttpResponse

/**
 * 예외(오류)에 대한 핸들링을 하기위한 Controller Class
 * */
@RestControllerAdvice
class ErrorController {

    @ExceptionHandler(Exception::class)
    fun exceptionHandler(exception: Exception): Response {

        val exceptionType = when(exception) {
            is Exceptions.DataNotFoundException -> ResponseStatus.DATA_NOT_FOUND
            else -> ResponseStatus.FAILED
        }

        if(exception !is NoResourceFoundException) {
            exception.printStackTrace()
        }

        return Response(exceptionType, exception.message)
    }
}