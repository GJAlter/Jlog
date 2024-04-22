package kr.j_jun.jlog.Util

import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.io.File

@Component
class FileUtil {

    @Value("\${kr.j_jun.jlog.value.attach-upload-path}")
    lateinit var uploadPath: String

    @PostConstruct
    fun existsFolder() {
        val file = File(uploadPath)
        if(!file.exists()) {
            file.mkdirs()
        }
    }
}