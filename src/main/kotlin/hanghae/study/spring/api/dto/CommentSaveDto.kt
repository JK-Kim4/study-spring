package hanghae.study.spring.api.dto

import hanghae.study.spring.domain.Comment
import hanghae.study.spring.domain.Member
import hanghae.study.spring.domain.Post
import jakarta.validation.constraints.NotEmpty
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter

@Getter @Setter
@NoArgsConstructor
class CommentSaveDto(

    @field:NotEmpty
    val content: String,

    @field:NotEmpty
    val postId: Long,

) {
    fun toComment(post: Post, member: Member) : Comment {
        return Comment(post = post, member = member, content = content)
    }
}
