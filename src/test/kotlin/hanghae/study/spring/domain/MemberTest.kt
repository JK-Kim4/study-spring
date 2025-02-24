package hanghae.study.spring.domain

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class MemberTest {

    @Test
    fun create_member_test(){
        var name = "John"
        var password = "password"
        val member = Member(name = name, password = password);

        Assertions.assertThat(member.name).isEqualTo(name)
        Assertions.assertThat(member.password).isEqualTo(password)
        Assertions.assertThat(member.id).isNull()
    }


}