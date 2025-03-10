package hanghae.study.spring.api.dto

import hanghae.study.spring.domain.Post
import lombok.Getter
import lombok.Setter
import java.time.LocalDateTime

@Getter @Setter
class PostListResponseDto(
    val id: Long?,
    val title: String?,
    val content: String?,
    //val authorName: String?,
    val createdAt: LocalDateTime?,
    val commentCount: Int?,
) {
    fun fromEntity(entity: Post): PostListResponseDto? {
        return PostListResponseDto(
            id = entity.id,
            title = entity.title,
            content = entity.content,
            //authorName = entity.authorName,
            commentCount = entity.comments?.size,
            createdAt = entity.createdAt,
        )
    }
}

