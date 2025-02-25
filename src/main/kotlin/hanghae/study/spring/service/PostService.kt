package hanghae.study.spring.service

import hanghae.study.spring.domain.Post
import org.springframework.stereotype.Service

@Service
 interface PostService {

    fun findPostList(): List<Post>

    fun findPostById(id: Long): Post?

    fun save(post: Post): Post

    fun update(id: Long, post: Post): Post

    fun deleteById(id: Long)

}