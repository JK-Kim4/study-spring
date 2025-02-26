package hanghae.study.spring.service

import hanghae.study.spring.api.dto.MemberSaveDto
import hanghae.study.spring.api.dto.MemberSigninDto
import org.junit.jupiter.api.Assertions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.Test

@SpringBootTest
class MemberServiceTest {

    @Autowired
    private lateinit var memberService: MemberService

    @Test
    fun login_success_test(){
        val dto = MemberSaveDto(name = "test", password = "1234")
        val loginMember = MemberSigninDto(name = "test", password = "1234")
        memberService.signup(dto);

        memberService.signin(loginMember)?.let { Assertions.assertTrue(it) };
    }



    @Test
    fun login_fail_test(){
        val dto = MemberSaveDto(name = "test", password = "1234")
        val loginMember = MemberSigninDto(name = "test", password = "5678")
        memberService.signup(dto);

        memberService.signin(loginMember)?.let { Assertions.assertFalse(it) };

    }
}