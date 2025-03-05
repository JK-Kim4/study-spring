package hanghae.study.spring.api.dto

import hanghae.study.spring.domain.Member
import hanghae.study.spring.domain.Role
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter

@Getter @Setter
@NoArgsConstructor
class MemberSaveDto(

    @field:NotEmpty
    val name: String,

    @field:NotEmpty
    @field:Size(min = 4, max = 18)
    val password: String,
    val role: Role
) {
    fun toMember() : Member = Member(
        name = name, password = password, role = role
    )
}
