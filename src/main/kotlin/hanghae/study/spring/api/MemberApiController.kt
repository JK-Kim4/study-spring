package hanghae.study.spring.api

import hanghae.study.spring.api.dto.JwtResponseDto
import hanghae.study.spring.api.dto.MemberSaveDto
import hanghae.study.spring.api.dto.MemberSigninDto
import hanghae.study.spring.api.spec.MemberApiSpec
import hanghae.study.spring.service.MemberService
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import lombok.RequiredArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequiredArgsConstructor
@RequestMapping("/v1/api/member")
class MemberApiController (private val memberService: MemberService) : MemberApiSpec{

    @PostMapping("/signup")
    override fun signup(@RequestBody @Valid memberSaveDto: MemberSaveDto): ResponseEntity<String> {
        return ResponseEntity.ok( memberService.signup(memberSaveDto)?.let { "success" })
    }

    @PostMapping("/signin")
    override fun signin(
        @RequestBody memberSigninDto: MemberSigninDto,
                        httpServletResponse: HttpServletResponse
    ): ResponseEntity<JwtResponseDto> {
        return ResponseEntity.ok( memberService.signin(memberSigninDto, httpServletResponse))
    }
}