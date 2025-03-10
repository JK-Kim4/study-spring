package hanghae.study.spring.service

import hanghae.study.spring.api.dto.CommentSaveDto
import hanghae.study.spring.common.jwt.JwtUtil
import hanghae.study.spring.domain.Member
import hanghae.study.spring.domain.Post
import hanghae.study.spring.repository.MemberJpaRepository
import hanghae.study.spring.repository.PostJpaRepository
import jakarta.servlet.http.HttpServletRequest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
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

    @Autowired
    lateinit var jwtUtil: JwtUtil


    @Test
    fun findCommentListByToken() {}

    @Test
    fun deleteById() {}

    @Test
    fun update() {}

    @Test
    fun save() {
        val content = "test content;"

        val member = Member(name = "test", password = "test")
        val savedMember = memberJpaRepository.save(member);
        val post = Post(title = "test title", content= "test content", member = savedMember)
        val savePost = postJpaRepository.save(post)



        val commentSaveDto = CommentSaveDto(content = content, postId = savePost.id!!)

        val comment = commentService.save(commentSaveDto, savedMember)

        Assertions.assertNotNull(comment)
        Assertions.assertEquals(comment.postId, savePost.id)
        Assertions.assertEquals(comment.memberId, savedMember.id)

    }
}
