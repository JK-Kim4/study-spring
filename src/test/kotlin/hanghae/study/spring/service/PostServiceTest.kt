package hanghae.study.spring.service

import hanghae.study.spring.api.dto.PostSaveDto
import hanghae.study.spring.api.dto.PostUpdateDto
import hanghae.study.spring.repository.PostJpaRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.Test

@SpringBootTest
class PostServiceTest {
    @Autowired
    lateinit var postService: PostService

    @Autowired
    lateinit var postJpaRepository: PostJpaRepository

    @AfterEach
    fun clean() {
        postJpaRepository.deleteAll()
    }

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
        val title = "ordered title"

        val post1 = PostSaveDto(title = "test title1", content = "test content1", password = "test password1", authorName = "test author1")
        val post2 = PostSaveDto(title = "test title2", content = "test content2", password = "test password2", authorName = "test author2")
        val post3 = PostSaveDto(title = "test title3", content = "test content3", password = "test password3", authorName = "test author3")
        val lastPost = PostSaveDto(title = title, content = "test content4", password = "test password4", authorName = "test author4")

        postService.save(post1)
        postService.save(post2)
        postService.save(post3)
        postService.save(lastPost)

        val posts = postService.findPostListOrderByIdDesc();
        val firstPost = posts.first()

        Assertions.assertThat(posts.size).isEqualTo(4)
        Assertions.assertThat(firstPost.title).isEqualTo(title)

    }

    @Test
    fun update_test_success(){
        val password = "1234";

        val post1 = PostSaveDto(title = "test title1", content = "test content1",
            password = password, authorName = "test author1")
        val savePost = postService.save(post1)

        val postUpdateDot = PostUpdateDto(title = "update title", content = "update content",
            password = password, authorName = "update author")

        val updatePost = savePost.id?.let { postService.update(it, postUpdateDot) }

        Assertions.assertThat(updatePost?.title).isNotEqualTo(post1.title)
        Assertions.assertThat(updatePost?.title).isEqualTo(postUpdateDot.title)
        Assertions.assertThat(updatePost?.content).isEqualTo(postUpdateDot.content)
    }

    @Test
    fun update_test_failure(){
        val password = "1234";
        val wrongPassword = "5678";

        val post1 = PostSaveDto(title = "test title1", content = "test content1",
            password = password, authorName = "test author1")
        val savePost = postService.save(post1)

        val postUpdateDot = PostUpdateDto(title = "update title", content = "update content",
            password = wrongPassword, authorName = "update author")

        org.junit.jupiter.api.Assertions.assertThrows(NoSuchElementException::class.java){
            savePost.id?.let { postService.update(it, postUpdateDot) }
        }
    }

    @Test
    fun delete_test(){
        val post1 = PostSaveDto(title = "test title1", content = "test content1",
            password = "1234", authorName = "test author1")
        val savePost = postService.save(post1)

        val savedPostId = savePost.id
        savedPostId?.let { postService.deleteById(it) }

        org.junit.jupiter.api.Assertions.assertThrows(NoSuchElementException::class.java){
            savedPostId?.let { postService.findPostById(it) }
        }
    }

}