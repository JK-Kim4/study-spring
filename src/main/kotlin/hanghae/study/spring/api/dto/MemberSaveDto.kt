package hanghae.study.spring.api.dto

import hanghae.study.spring.domain.Member
import hanghae.study.spring.domain.Role
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter

@Getter @Setter
@NoArgsConstructor
class MemberSaveDto(

    val name: String,
    val password: String,
    val role: Role
) {
    fun toMember() : Member = Member(
        name = name, password = password, role = role
    )
}
