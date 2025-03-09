package hanghae.study.spring.repository

import hanghae.study.spring.domain.Comment
import org.springframework.data.jpa.repository.JpaRepository

interface CommentJpaRepository : JpaRepository<Comment, Long>{
}