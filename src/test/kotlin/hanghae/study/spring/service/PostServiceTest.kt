package hanghae.study.spring.service

import hanghae.study.spring.api.dto.PostSaveDto
import hanghae.study.spring.api.dto.PostUpdateDto
import hanghae.study.spring.domain.Member
import hanghae.study.spring.repository.MemberJpaRepository
import hanghae.study.spring.repository.PostJpaRepository
import jakarta.servlet.http.HttpServletRequest
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
class PostServiceTest {
    @Autowired
    lateinit var postService: PostService

    @Autowired
    lateinit var postJpaRepository: PostJpaRepository

    @Autowired
    lateinit var memberJpaRepository: MemberJpaRepository

    @Autowired
    lateinit var httpServletRequest: HttpServletRequest

    lateinit var member: Member

    @BeforeEach
    fun setMember(){
        member = memberJpaRepository.save(Member(name = "test", password = "test"))
    }

    @AfterEach
    fun clean() {
        postJpaRepository.deleteAll()
    }

    @Test
    fun save_post_test() {

        val title = "test title"
        val content = "test content"

        val postSaveDto = PostSaveDto(title, content)
        val savedPost = postService.save(postSaveDto, member)

        Assertions.assertThat(title).isEqualTo(savedPost.title)
    }

    @Test
    fun find_by_id_test(){

        val title = "test title"
        val content = "test content"

        val postSaveDto = PostSaveDto(title, content)
        val postId = postService.save(postSaveDto, member).id
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

        postService.save(post1,member)
        postService.save(post2,member)
        postService.save(post3,member)
        postService.save(post4,member)

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

        postService.save(post1,member)
        postService.save(post2,member)
        postService.save(post3,member)
        postService.save(lastPost,member)

        val posts = postService.findPostListOrderByIdDesc();
        val firstPost = posts.first()

        Assertions.assertThat(posts.size).isEqualTo(4)
        Assertions.assertThat(firstPost.title).isEqualTo(title)

    }

    @Test @Transactional
    fun update_test_success(){
        val password = "1234";

        val post1 = PostSaveDto(title = "test title1", content = "test content1")
        val savePost = postService.save(post1,member)

        val postUpdateDot = PostUpdateDto(title = "update title", content = "update content",
            password = password, authorName = "update author")

        val updatePost = postService.update(savePost.id, postUpdateDot, member)

        Assertions.assertThat(updatePost?.title).isNotEqualTo(post1.title)
        Assertions.assertThat(updatePost?.title).isEqualTo(postUpdateDot.title)
        Assertions.assertThat(updatePost?.content).isEqualTo(postUpdateDot.content)
    }

    @Test @Transactional
    fun delete_test(){
        val post1 = PostSaveDto(title = "test title1", content = "test content1")
        val savePost = postService.save(post1,member)

        postService.deleteById(savePost.id, member)

        org.junit.jupiter.api.Assertions.assertThrows(NoSuchElementException::class.java){
            postService.findPostById(savePost.id)
        }
    }

}