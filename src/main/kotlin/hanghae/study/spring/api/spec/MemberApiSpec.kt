package hanghae.study.spring.api.spec

import hanghae.study.spring.api.dto.JwtResponseDto
import hanghae.study.spring.api.dto.MemberSaveDto
import hanghae.study.spring.api.dto.MemberSigninDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity

@Tag(name = "회원 API", description = "회원 관리 API")
interface MemberApiSpec {

    @Operation( summary =       "회원가입 API", method = "POST",
        description =   "- 제목, 작성자명, 작성 내용, 작성 날짜를 조회하기\n" +
                "- 작성 날짜 기준 내림차순으로 정렬하기")
    fun signup(memberSaveDto: MemberSaveDto) : ResponseEntity<String>

    @Operation( summary =       "로그인 API", method = "POST",
        description =   "- 제목, 작성자명, 작성 내용, 작성 날짜를 조회하기\n" +
                "- 작성 날짜 기준 내림차순으로 정렬하기")
    fun signin(memberSigninDto: MemberSigninDto,
               httpServletResponse: HttpServletResponse) : ResponseEntity<JwtResponseDto>
}