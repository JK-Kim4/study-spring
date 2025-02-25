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

    @Test
    fun find_all_test(){
        val post1 = PostSaveDto(title = "test title1", content = "test content1", password = "test password1", authorName = "test author1")
        val post2 = PostSaveDto(title = "test title2", content = "test content2", password = "test password2", authorName = "test author2")
        val post3 = PostSaveDto(title = "test title3", content = "test content3", password = "test password3", authorName = "test author3")
        val post4 = PostSaveDto(title = "test title4", content = "test content4", password = "test password4", authorName = "test author4")

        postService.save(post1)
        postService.save(post2)
        postService.save(post3)
        postService.save(post4)

        val posts = postService.findPostList();
        val lastPost = posts.last()

        Assertions.assertThat(posts.size).isEqualTo(4)
        Assertions.assertThat(lastPost.title).isEqualTo("test title4")
    }

    @Test
    fun find_all_ordered_test(){

    }

    @Test
    fun update_test(){


    }

    @Test
    fun delete_test(){

    }

}