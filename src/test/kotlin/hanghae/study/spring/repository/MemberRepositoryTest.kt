package hanghae.study.spring.repository
import hanghae.study.spring.domain.Member
import org.junit.jupiter.api.Assertions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.Test

@SpringBootTest
class MemberRepositoryTest {


    @Autowired
    val memberRepository : MemberRepository? = null

    @Test
    fun save_member_test(){
        val member = Member(name = "test", password = "test")

        val savedMember = memberRepository!!.save(member);

        Assertions.assertNotNull(savedMember)
        Assertions.assertEquals(member.name, savedMember.name)
    }

}