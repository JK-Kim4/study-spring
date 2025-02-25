package hanghae.study.spring.service

import hanghae.study.spring.api.dto.PostSaveDto
import org.assertj.core.api.Assertions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.Test

@SpringBootTest
class PostServiceTest {
    @Autowired
    lateinit var postService: PostService

    @Test
    fun save_post_test() {
        val title = "test title"
        val content = "test content"
        val password = "test password"
        val authorName = "test author"

        val postSaveDto = PostSaveDto(title, content, password, authorName)
        val savedPost = postService.save(postSaveDto)

        Assertions.assertThat(title).isEqualTo(savedPost.title)
    }

    @Test
    fun find_by_id_test(){
        val title = "test title"
        val content = "test content"
        val password = "test password"
        val authorName = "test author"

        val postSaveDto = PostSaveDto(title, content, password, authorName)
        val postId = postService.save(postSaveDto).id;
        val post = postId?.let { postService.findPostById(it) }

        Assertions.assertThat(post?.title).isEqualTo(title)
        Assertions.assertThat(post?.content).isEqualTo(content)
        Assertions.assertThat(post?.password).isEqualTo(password)
        Assertions.assertThat(post?.authorName).isEqualTo(authorName)
    }

}