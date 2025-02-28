package hanghae.study.spring.service

import hanghae.study.spring.api.dto.JwtResponseDto
import hanghae.study.spring.api.dto.MemberSaveDto
import hanghae.study.spring.api.dto.MemberSigninDto
import hanghae.study.spring.domain.Member
import jakarta.servlet.http.HttpServletResponse

interface MemberService {

    fun signup(memberSaveDto : MemberSaveDto) : Member?

    fun signin(memberSignInDto : MemberSigninDto, response: HttpServletResponse) : JwtResponseDto?
}