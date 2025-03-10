package hanghae.study.spring.api.dto

import hanghae.study.spring.domain.Comment
import lombok.Getter
import lombok.Setter

@Getter @Setter
class CommentListResponseDto(

    val commentId: Long?,

    val postId : Long?,

    val memberId: Long?,

    val memberName: String,

    val content: String,

) {

    fun fromComment(comment: Comment) : CommentListResponseDto {
        return CommentListResponseDto(
            commentId = comment.id, postId = comment.post.id,
            memberId = comment.member.id, memberName = comment.member.name,
            content = comment.content
        )

    }


}