package hanghae.study.spring.api.dto

import hanghae.study.spring.domain.Member
import lombok.Getter
import lombok.Setter

@Getter @Setter
class MemberSaveDto(
    val name: String,
    val password: String,
) {
    fun toMember() : Member = Member(
        name = name, password = password,
    )
}
