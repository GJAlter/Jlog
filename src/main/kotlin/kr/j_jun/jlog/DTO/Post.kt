package kr.j_jun.jlog.DTO

import kr.j_jun.jlog.Entity.Posts
import java.util.Date

class Post {

    data class Post(
        var title: String,
        var content: String,
        var attaches: List<String>? = null
    )

    data class Item(
        var id: String,
        var title: String,
        var contentPreview: String,
        var modifiedDatetime: Date
    )

    data class Detail(
        var id: String,
        var userId: String,
        var title: String,
        var content: String,
        var attaches: List<String>? = null,
        var modifiedDatetime: Date,
        var comments: List<Any>? = null,
    )

    data class Update(
        var title: String,
        var content: String,
        var attaches: List<String>? = null
    )

    data class DeleteAttach(
        var id: String,
        var fileNames: List<String>
    )

    companion object {

        fun toItem(post: Posts): Item {
            return Item(
                id = post.id!!,
                title = post.title,
                contentPreview = if(post.content.length > 50) "${post.content.substring(0, 50)}..." else post.content,
                modifiedDatetime = post.modifiedDatetime
            )
        }

        fun toDetail(post: Posts): Detail {
            return Detail(
                id = post.id!!,
                userId = post.userId,
                title = post.title,
                content = post.content,
                modifiedDatetime = post.modifiedDatetime,
                attaches = post.attaches,
                comments = post.comments
            )
        }

    }
}