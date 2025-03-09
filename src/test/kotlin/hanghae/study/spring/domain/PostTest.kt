package hanghae.study.spring.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PostTest {

    @Test
    fun post_create_test(){
        val title = "test title"
        val content = "test content"
        val authorName = "test author"
        val password = "test password"

        val post = Post(title = title, content = content)

        assertThat(post.title).isEqualTo(title)
        assertThat(post.content).isEqualTo(content)
        assertThat(post.id).isNull()
    }

}