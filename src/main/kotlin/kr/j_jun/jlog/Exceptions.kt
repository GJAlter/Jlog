package kr.j_jun.jlog


/**
 * 예외, 오류 등에 대해 정의 하는 클래스 */
class Exceptions {

    class DataNotFoundException(msg: String): Exception(msg)
}