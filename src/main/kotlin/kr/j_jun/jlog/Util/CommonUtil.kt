package kr.j_jun.jlog.Util

import org.springframework.data.domain.Page

class CommonUtil {

    companion object {

        fun Int?.toPage(): Int {
            if(this == null || this < 2) {
                return 0
            }
            return this - 1
        }

        fun <T> Page<T>.toMap(): Map<String, Any> {
            return mapOf("data" to this.content, "totalPage" to this.totalPages)
        }

    }

}