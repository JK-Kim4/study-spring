package hanghae.study.spring.repository

import hanghae.study.spring.domain.Comment
import hanghae.study.spring.domain.Member
import hanghae.study.spring.domain.Post
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class CommentRepositoryTest {

    @Autowired
    private lateinit var memberJpaRepository: MemberJpaRepository
    @Autowired
    private lateinit var commentJpaRepository: CommentJpaRepository
    @Autowired
    private lateinit var postJpaRepository: PostJpaRepository


    @BeforeEach
    fun set_post_member(){
        val member = Member(name = "test", password = "test")
        memberJpaRepository.save(member);

        val post = Post(title = "test", content = "test", member = member)
        postJpaRepository.save(post);
    }


    @DisplayName("댓글 저장 테스트")
    @Test
    fun comment_insert_test() {

        val member = memberJpaRepository.findById(1).get()
        val post = postJpaRepository.findById(1).get()

        var comment = Comment(content = "test", member = member, post = post)

        comment = commentJpaRepository.save(comment);

        Assertions.assertNotNull(comment.id)
    }
}