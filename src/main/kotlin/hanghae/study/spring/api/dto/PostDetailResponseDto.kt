package hanghae.study.spring.api.dto

import lombok.Getter
import lombok.Setter
import java.time.LocalDateTime

@Getter @Setter
class PostDetailResponseDto(
    val id: Long,
    val title: String?,
    val content: String?,
    val memberId: Long?,
    val comments: List<CommentListResponseDto>,
    val createdAt: LocalDateTime?,
) {
}
