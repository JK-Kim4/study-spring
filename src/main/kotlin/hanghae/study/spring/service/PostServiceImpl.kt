package hanghae.study.spring.service

import hanghae.study.spring.api.dto.PostSaveDto
import hanghae.study.spring.api.dto.PostUpdateDto
import hanghae.study.spring.domain.Post
import hanghae.study.spring.repository.PostJpaRepository
import lombok.RequiredArgsConstructor
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class PostServiceImpl(private val postRepository: PostJpaRepository) : PostService {

    override fun findPostList(): List<Post> {
        return postRepository.findAll()
    }

    override fun findPostListOrderByIdDesc(): List<Post> {
        return postRepository.findByOrderByIdDesc()
    }

    override fun findPostById(id: Long): Post? {
        return postRepository.findByIdOrNull(id)
    }

    override fun save(postSaveDto: PostSaveDto): Post {
        return postRepository.save(postSaveDto.toPost())
    }

    override fun update(id: Long, postUpdateDto: PostUpdateDto): Post? {
        val post = findPostById(id)

        if(post?.password == postUpdateDto.password) post.update(postUpdateDto)

        return post
    }

    override fun deleteById(id: Long) {
        postRepository.deleteById(id)
    }
}