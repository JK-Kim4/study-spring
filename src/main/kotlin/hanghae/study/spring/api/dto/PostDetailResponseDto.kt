package hanghae.study.spring.api.dto

import hanghae.study.spring.domain.Post
import lombok.Getter
import lombok.Setter
import java.time.LocalDateTime

@Getter @Setter
class PostDetailResponseDto(
    val id: Long?,
    val title: String?,
    val content: String?,
    val authorName: String?,
    val createdAt: LocalDateTime?,
) {
    fun fromEntity(entity: Post): PostDetailResponseDto? {
        return PostDetailResponseDto(
            id = entity.id,
            title = entity.title,
            content = entity.content,
            authorName = entity.authorName,
            createdAt = entity.createdAt,
        )
    }
}
