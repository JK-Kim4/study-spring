package hanghae.study.spring.service


import hanghae.study.spring.api.dto.MemberSaveDto
import hanghae.study.spring.api.dto.MemberSigninDto
import hanghae.study.spring.common.exception.MemberNameDuplicateException
import hanghae.study.spring.common.exception.MemberNotFoundException
import hanghae.study.spring.domain.Member
import hanghae.study.spring.repository.MemberRepository
import lombok.RequiredArgsConstructor
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
@RequiredArgsConstructor
class MemberServiceImpl(private val memberRepository: MemberRepository) : MemberService {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    override fun signup(memberSaveDto: MemberSaveDto): Member? {

        if(memberRepository.findByName(memberSaveDto.name).isPresent) {
            throw MemberNameDuplicateException("이미 사용중인 계정(이름)입니다.")
        }

        return memberRepository.save(memberSaveDto.toMember())
    }

    override fun signin(memberSignInDto: MemberSigninDto): Boolean? {
        val member : Member = memberRepository.findByName(memberSignInDto.name)
            .orElseThrow { MemberNotFoundException("등록되지 않은 사용자입니다.") }

        if(member.password == memberSignInDto.password) {
            logger.info("login success!!")
            TODO("[#4] 로그인 성공 시 JWT를 생성하고 반환합니다.")
            return true
        }

        return false
    }
}