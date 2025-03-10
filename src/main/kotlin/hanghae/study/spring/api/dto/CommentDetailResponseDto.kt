package hanghae.study.spring.api.dto

import hanghae.study.spring.domain.Comment
import lombok.Getter
import lombok.Setter
import java.time.LocalDateTime

@Getter @Setter
class CommentDetailResponseDto(
    var id: Long? = null,

    var content: String,

    var postId: Long? = null,

    var memberId: Long? = null,

    var memberName: String? = null,

    var createdAt: LocalDateTime? = null,

    ) {

    fun fromEntity(comment: Comment): CommentDetailResponseDto {
        return CommentDetailResponseDto(
            id = comment.id,
            content = comment.content,
            postId = comment.post.id,
            memberId = comment.member.id,
            memberName = comment.member.name,
            createdAt = comment.createdAt
        )
    }
}