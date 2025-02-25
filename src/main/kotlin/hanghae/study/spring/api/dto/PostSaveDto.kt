package hanghae.study.spring.api.dto

import hanghae.study.spring.domain.Post
import lombok.Getter
import lombok.Setter

@Getter @Setter
class PostSaveDto (
    val title: String,

    val content: String,

    val password: String,

    val authorName: String,
) {

    fun toPost(): Post = Post(
        title = title, content = content,
        password = password, authorName = authorName)
}
