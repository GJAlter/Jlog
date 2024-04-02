package kr.j_jun.jlog.DTO

import kr.j_jun.jlog.Entity.Posts
import java.util.Date

class Post {

    data class Post(
        var title: String,
        var content: String,
    )

    data class Item(
        var id: String,
        var title: String,
        var modifiedDatetime: Date
    )

    companion object {

        fun toItem(post: Posts): Item {
            return Item(
                id = post.id!!,
                title = post.title,
                modifiedDatetime = post.modifiedDatetime
            )
        }

    }
}