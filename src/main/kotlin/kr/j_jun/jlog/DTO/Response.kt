package kr.j_jun.jlog.DTO

import kr.j_jun.jlog.Enum.ResponseStatus
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

/**
 * 일관적인 데이터 Return을 위한 Class 생성
 * @param status ResponseStatus에 정의된 결과 종류
 * @param data Response에 포함할 결과값, 결과가 없을경우 빈 String으로 반환
 * */
class Response(status: ResponseStatus, data: Any? = ""):
    ResponseEntity<Any>(ResponseData(status.statusCode, "${status.name} - ${status.msg}", data), HttpStatus.OK) {

    data class ResponseData(
        val statusCode: Int,
        val message: String,
        val result: Any?
    )
}