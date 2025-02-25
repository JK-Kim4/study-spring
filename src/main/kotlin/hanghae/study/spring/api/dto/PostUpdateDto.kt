package hanghae.study.spring.api.dto

import lombok.Getter
import lombok.Setter

@Getter @Setter
class PostUpdateDto (
    val title: String,
    val content: String,
    val authorName: String,
    val password: String,
)
