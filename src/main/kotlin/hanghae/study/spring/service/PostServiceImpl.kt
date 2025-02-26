package hanghae.study.spring.service

import hanghae.study.spring.api.dto.PostDetailResponseDto
import hanghae.study.spring.api.dto.PostListResponseDto
import hanghae.study.spring.api.dto.PostSaveDto
import hanghae.study.spring.api.dto.PostUpdateDto
import hanghae.study.spring.repository.PostJpaRepository
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class PostServiceImpl(private val postRepository: PostJpaRepository) : PostService {

    override fun findPostList(): List<PostListResponseDto> {
        val postList = postRepository.findAll()

        return postList
            .map { PostListResponseDto(it.id, it.title, it.content, it.authorName, it.createdAt) }
            .toList()
    }

    override fun findPostListOrderByIdDesc(): List<PostListResponseDto> {
        val postList = postRepository.findByOrderByIdDesc()

        return postList
            .map { PostListResponseDto(it.id, it.title, it.content, it.authorName, it.createdAt) }
            .toList()
    }

    override fun findPostById(id: Long): PostDetailResponseDto? {
        val post = postRepository.findById(id).orElseThrow()
        return PostDetailResponseDto(post.id, post.title, post.content, post.authorName, post.createdAt)
    }

    override fun save(postSaveDto: PostSaveDto): PostDetailResponseDto? {
        val post = postRepository.save(postSaveDto.toPost())

        return PostDetailResponseDto(post.id, post.title, post.content, post.authorName, post.createdAt)
    }

    override fun update(id: Long, postUpdateDto: PostUpdateDto): PostDetailResponseDto? {
        val post = postRepository.findByIdAndPassword(id, postUpdateDto.password).orElseThrow();

        post.update(postUpdateDto)

        return PostDetailResponseDto(post.id, post.title, post.content, post.authorName, post.createdAt)
    }

    override fun deleteById(id: Long): String?{
        postRepository.deleteById(id)

        return "success"
    }
}