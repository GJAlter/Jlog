package kr.j_jun.jlog.Enum

/**
 * Response 결과에 대한 사전 정의 Enum 클래스
 * @param statusCode 결과 코드
 * @param msg 결과코드에 따른 간단 정의
 * */
enum class ResponseStatus(val statusCode: Int, val msg: String) {

    FAILED(100, "기타 오류"),
    DATA_NOT_FOUND(101, "데이터 오류"),
    DUPLICATE_DATA(102, "중복 오류"),

    OK(200, "정상 처리")
}