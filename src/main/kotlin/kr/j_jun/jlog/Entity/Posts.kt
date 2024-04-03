package kr.j_jun.jlog.Entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.Date

@Document(collection = "posts")
class Posts(
    @Id
    var id: String? = null,
    var userId: String,
    var title: String,
    var content: String,
    var createdDatetime: Date = Date(),
    var modifiedDatetime: Date = Date(),
    var comments: List<Any>? = null,
    var attaches: List<String>? = null,
) {
}