package hanghae.study.spring.service

import hanghae.study.spring.repository.MemberJpaRepository
import hanghae.study.spring.repository.PostJpaRepository
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class CommentServiceTest {

    @Autowired
    lateinit var commentService: CommentService

    @Autowired
    lateinit var postJpaRepository: PostJpaRepository

    @Autowired
    lateinit var memberJpaRepository: MemberJpaRepository

    @Autowired
    lateinit var httpServletRequest: HttpServletRequest

}