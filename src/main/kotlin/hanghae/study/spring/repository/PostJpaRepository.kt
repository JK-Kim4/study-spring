package hanghae.study.spring.repository

import hanghae.study.spring.domain.Post
import org.springframework.data.jpa.repository.JpaRepository

interface PostJpaRepository : JpaRepository<Post, Int> {
}