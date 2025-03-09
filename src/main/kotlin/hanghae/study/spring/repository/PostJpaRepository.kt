package hanghae.study.spring.repository

import hanghae.study.spring.domain.Post
import org.springframework.data.jpa.repository.JpaRepository

interface PostJpaRepository : JpaRepository<Post, Long> {

    fun findByOrderByIdDesc(): List<Post>

    //fun findByIdAndPassword(id: Long, password: String) : Optional<Post>
}