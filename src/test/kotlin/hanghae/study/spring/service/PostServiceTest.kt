package hanghae.study.spring.service

import hanghae.study.spring.api.dto.PostSaveDto
import hanghae.study.spring.api.dto.PostUpdateDto
import hanghae.study.spring.repository.PostJpaRepository
import jakarta.servlet.http.HttpServletRequest
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

    @Autowired
    lateinit var httpServletRequest: HttpServletRequest

    @AfterEach
    fun clean() {
        postJpaRepository.deleteAll()
    }

    @Test
    fun save_post_test() {
        val title = "test title"
        val content = "test content"

        val postSaveDto = PostSaveDto(title, content)
        val savedPost = postService.save(postSaveDto, httpServletRequest)

        Assertions.assertThat(title).isEqualTo(savedPost!!.title)
    }

    @Test
    fun find_by_id_test(){
        val title = "test title"
        val content = "test content"
        val password = "test password"
        val authorName = "test author"

        val postSaveDto = PostSaveDto(title, content)
        val postId = postService.save(postSaveDto, httpServletRequest)!!.id;
        val post = postId?.let { postService.findPostById(it) }

        Assertions.assertThat(post!!.title).isEqualTo(title)
        Assertions.assertThat(post!!.content).isEqualTo(content)
    }

    @Test
    fun find_all_test(){
        val post1 = PostSaveDto(title = "test title1", content = "test content1")
        val post2 = PostSaveDto(title = "test title2", content = "test content2")
        val post3 = PostSaveDto(title = "test title3", content = "test content3")
        val post4 = PostSaveDto(title = "test title4", content = "test content4")

        postService.save(post1,httpServletRequest)
        postService.save(post2,httpServletRequest)
        postService.save(post3,httpServletRequest)
        postService.save(post4,httpServletRequest)

        val posts = postService.findPostList();
        val lastPost = posts.last()

        Assertions.assertThat(posts.size).isEqualTo(4)
        Assertions.assertThat(lastPost.title).isEqualTo("test title4")
    }

    @Test
    fun find_all_ordered_test(){
        val title = "ordered title"

        val post1 = PostSaveDto(title = "test title1", content = "test content1")
        val post2 = PostSaveDto(title = "test title2", content = "test content2")
        val post3 = PostSaveDto(title = "test title3", content = "test content3")
        val lastPost = PostSaveDto(title = title, content = "test content4")

        postService.save(post1,httpServletRequest)
        postService.save(post2,httpServletRequest)
        postService.save(post3,httpServletRequest)
        postService.save(lastPost,httpServletRequest)

        val posts = postService.findPostListOrderByIdDesc();
        val firstPost = posts.first()

        Assertions.assertThat(posts.size).isEqualTo(4)
        Assertions.assertThat(firstPost.title).isEqualTo(title)

    }

    @Test
    fun update_test_success(){
        val password = "1234";

        val post1 = PostSaveDto(title = "test title1", content = "test content1")
        val savePost = postService.save(post1,httpServletRequest)

        val postUpdateDot = PostUpdateDto(title = "update title", content = "update content",
            password = password, authorName = "update author")

        val updatePost = savePost!!.id?.let { postService.update(it, postUpdateDot,httpServletRequest) }

        Assertions.assertThat(updatePost?.title).isNotEqualTo(post1.title)
        Assertions.assertThat(updatePost?.title).isEqualTo(postUpdateDot.title)
        Assertions.assertThat(updatePost?.content).isEqualTo(postUpdateDot.content)
    }

    @Test
    fun update_test_failure(){
        val password = "1234";
        val wrongPassword = "5678";

        val post1 = PostSaveDto(title = "test title1", content = "test content1")
        val savePost = postService.save(post1,httpServletRequest)

        val postUpdateDot = PostUpdateDto(title = "update title", content = "update content",
            password = wrongPassword, authorName = "update author")

        org.junit.jupiter.api.Assertions.assertThrows(NoSuchElementException::class.java){
            savePost!!.id?.let { postService.update(it, postUpdateDot,httpServletRequest) }
        }
    }

    @Test
    fun delete_test(){
        val post1 = PostSaveDto(title = "test title1", content = "test content1")
        val savePost = postService.save(post1,httpServletRequest)

        val savedPostId = savePost!!.id
        savedPostId?.let { postService.deleteById(it,httpServletRequest) }

        org.junit.jupiter.api.Assertions.assertThrows(NoSuchElementException::class.java){
            savedPostId?.let { postService.findPostById(it) }
        }
    }

}