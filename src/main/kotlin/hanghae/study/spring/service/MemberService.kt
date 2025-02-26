package hanghae.study.spring.service

import hanghae.study.spring.api.dto.MemberSaveDto
import hanghae.study.spring.api.dto.MemberSigninDto
import hanghae.study.spring.domain.Member

interface MemberService {

    fun signup(memberSaveDto : MemberSaveDto) : Member?

    fun signin(memberSignInDto : MemberSigninDto) : Boolean? = false
}