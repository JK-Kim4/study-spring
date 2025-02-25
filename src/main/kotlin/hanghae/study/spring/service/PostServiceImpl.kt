package hanghae.study.spring.service

import hanghae.study.spring.domain.Post
import hanghae.study.spring.repository.PostJpaRepository
import lombok.RequiredArgsConstructor

@RequiredArgsConstructor
class PostServiceImpl(private val postRepository: PostJpaRepository) : PostService {

    override fun findPostList(): List<Post> {
        TODO("Not yet implemented")
    }

    override fun findPostById(id: Long): Post? {
        TODO("Not yet implemented")
    }

    override fun save(post: Post): Post {
        TODO("Not yet implemented")
    }

    override fun update(id: Long, post: Post): Post {
        TODO("Not yet implemented")
    }

    override fun deleteById(id: Long) {
        TODO("Not yet implemented")
    }
}