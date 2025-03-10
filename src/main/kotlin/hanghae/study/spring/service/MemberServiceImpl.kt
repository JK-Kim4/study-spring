package hanghae.study.spring.service


import hanghae.study.spring.api.dto.JwtResponseDto
import hanghae.study.spring.api.dto.MemberSaveDto
import hanghae.study.spring.api.dto.MemberSigninDto
import hanghae.study.spring.common.exception.MemberNameDuplicateException
import hanghae.study.spring.common.exception.MemberNotFoundException
import hanghae.study.spring.common.exception.MemberSigninFailException
import hanghae.study.spring.common.jwt.JwtUtil
import hanghae.study.spring.domain.Member
import hanghae.study.spring.repository.MemberJpaRepository
import jakarta.servlet.http.HttpServletResponse
import lombok.RequiredArgsConstructor
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
class MemberServiceImpl(
    private val memberJpaRepository: MemberJpaRepository,
    private val jwtUtil: JwtUtil) : MemberService {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Transactional(readOnly = false)
    override fun signup(memberSaveDto: MemberSaveDto): Member? {

        if(memberJpaRepository.findByName(memberSaveDto.name).isPresent) {
            throw MemberNameDuplicateException("이미 사용중인 계정(이름)입니다.")
        }

        return memberJpaRepository.save(memberSaveDto.toMember())
    }

    override fun signin(memberSignInDto: MemberSigninDto, response: HttpServletResponse): JwtResponseDto {
        val member : Member = memberJpaRepository.findByName(memberSignInDto.name)
            .orElseThrow { MemberNotFoundException("등록되지 않은 사용자입니다.") }

        if(member.password == memberSignInDto.password) {
            logger.info("login success!!")

            response.addHeader(jwtUtil.AUTHORIZATION_HEADER,
                member.role?.let { jwtUtil.createToken(member.name, it) })
            return JwtResponseDto("success", LocalDateTime.now())
        }
        throw MemberSigninFailException("로그인 정보가 올바르지않습니다.");
    }
}