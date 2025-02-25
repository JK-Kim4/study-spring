package hanghae.study.spring.service

import hanghae.study.spring.api.dto.PostSaveDto
import hanghae.study.spring.api.dto.PostUpdateDto
import hanghae.study.spring.domain.Post
import org.springframework.stereotype.Service

@Service
 interface PostService {

    fun findPostList(): List<Post>

    fun findPostListOrderByIdDesc(): List<Post>

    fun findPostById(id: Long): Post?

    fun save(postSaveDto: PostSaveDto): Post

    fun update(id: Long, postUpdateDto: PostUpdateDto): Post?

    fun deleteById(id: Long)

}