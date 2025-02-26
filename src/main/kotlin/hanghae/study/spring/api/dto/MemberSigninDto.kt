package hanghae.study.spring.api.dto

import lombok.Getter
import lombok.Setter

@Getter @Setter
class MemberSigninDto(
    val name : String,
    val password : String
) {

}
