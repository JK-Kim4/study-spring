package hanghae.study.spring.service

import hanghae.study.spring.api.dto.PostDetailResponseDto
import hanghae.study.spring.api.dto.PostListResponseDto
import hanghae.study.spring.api.dto.PostSaveDto
import hanghae.study.spring.api.dto.PostUpdateDto


interface PostService {

    fun findPostList(): List<PostListResponseDto>

    fun findPostListOrderByIdDesc(): List<PostListResponseDto>

    fun findPostById(id: Long): PostDetailResponseDto?

    fun save(postSaveDto: PostSaveDto): PostDetailResponseDto?

    fun update(id: Long, postUpdateDto: PostUpdateDto): PostDetailResponseDto?

    fun deleteById(id: Long): String?

}