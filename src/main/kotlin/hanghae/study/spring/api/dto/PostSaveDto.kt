package hanghae.study.spring.api.dto

import hanghae.study.spring.domain.Post
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter

@Getter @Setter
@NoArgsConstructor
class PostSaveDto (

    @field:NotBlank
    @field:Size(min = 1, max = 120)
    val title: String,

    val content: String,

    @field:NotBlank
    @field:Size(min = 4, max = 16)
    val password: String,

    val authorName: String,
) {

    fun toPost(): Post = Post(
        title = title, content = content,
        password = password, authorName = authorName)
}
