package hanghae.study.spring.repository

import hanghae.study.spring.domain.Member
import hanghae.study.spring.domain.Post
import org.assertj.core.api.Assertions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.Test

@SpringBootTest
class PostRepositoryTest {

    @Autowired
    lateinit var postJpaRepository: PostJpaRepository

    @Autowired
    lateinit var memberJpaRepository: MemberJpaRepository

    @Test
    fun save() {

        val member = Member(name = "test", password = "test")

        val savedMember = memberJpaRepository.save(member);

        org.junit.jupiter.api.Assertions.assertNotNull(savedMember)
        org.junit.jupiter.api.Assertions.assertEquals(member.name, savedMember.name)

        val title = "test title"
        val content = "test content"
        val post = Post(title = title, content= content, member = savedMember)


        val savePost = postJpaRepository.save(post)
        Assertions.assertThat(post.title).isEqualTo(savePost.title)
        Assertions.assertThat(post.id).isNotNull()
        Assertions.assertThat(post.id).isEqualTo(savePost.id)

    }

}