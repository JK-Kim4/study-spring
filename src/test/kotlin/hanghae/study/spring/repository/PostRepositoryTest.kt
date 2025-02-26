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
    val postJpaRepository: PostJpaRepository? = null

    @Test
    fun save() {
        val title = "test title"
        val content = "test content"
        val password = "test password"
        val authorName = "test author"
        val post = Post(title = title, content= content, password= password, authorName= authorName)


        val savePost = postJpaRepository?.save(post)
        Assertions.assertThat(post.title).isEqualTo(savePost?.title)
        Assertions.assertThat(post.id).isNotNull()
        Assertions.assertThat(post.id).isEqualTo(savePost?.id!!)
    }

}