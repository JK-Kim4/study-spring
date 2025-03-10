package hanghae.study.spring.api.dto

import jakarta.validation.constraints.NotEmpty
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter

@Getter @Setter
@NoArgsConstructor
class CommentUpdateDto(

    @field:NotEmpty
    val content: String,

)
