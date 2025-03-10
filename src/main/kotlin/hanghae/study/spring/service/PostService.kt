package hanghae.study.spring.service

import hanghae.study.spring.api.dto.PostDetailResponseDto
import hanghae.study.spring.api.dto.PostListResponseDto
import hanghae.study.spring.api.dto.PostSaveDto
import hanghae.study.spring.api.dto.PostUpdateDto
import hanghae.study.spring.domain.Member


interface PostService {

    fun findPostList(): List<PostListResponseDto>

    fun findPostListOrderByIdDesc(): List<PostListResponseDto>

    fun findPostById(id: Long): PostDetailResponseDto

    fun save(postSaveDto: PostSaveDto, member: Member): PostDetailResponseDto

    fun update(postId: Long, postUpdateDto: PostUpdateDto, member: Member): PostDetailResponseDto

    fun deleteById(id: Long, member: Member): String

}