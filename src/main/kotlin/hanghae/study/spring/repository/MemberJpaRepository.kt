package hanghae.study.spring.repository

import hanghae.study.spring.domain.Member
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MemberJpaRepository : JpaRepository<Member, Long> {

    fun findByName(name: String): Optional<Member>

}