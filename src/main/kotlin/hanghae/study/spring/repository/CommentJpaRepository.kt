package hanghae.study.spring.repository

import hanghae.study.spring.domain.Comment
import hanghae.study.spring.domain.Member
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface CommentJpaRepository : JpaRepository<Comment, Long>{

    fun findByMember(member: Member): Optional<Comment>

}