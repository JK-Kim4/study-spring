package hanghae.study.spring.api.spec

import hanghae.study.spring.api.dto.JwtResponseDto
import hanghae.study.spring.api.dto.MemberSaveDto
import hanghae.study.spring.api.dto.MemberSigninDto
import jakarta.validation.Valid

interface MemberApiSpec {

    fun signup(@Valid memberSaveDto: MemberSaveDto) : String

    fun signin(@Valid memberSigninDto: MemberSigninDto) : JwtResponseDto
}