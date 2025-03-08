package hanghae.study.spring.service

import hanghae.study.spring.api.dto.PostDetailResponseDto
import hanghae.study.spring.api.dto.PostListResponseDto
import hanghae.study.spring.api.dto.PostSaveDto
import hanghae.study.spring.api.dto.PostUpdateDto
import jakarta.servlet.http.HttpServletRequest


interface PostService {

    fun findPostList(): List<PostListResponseDto>

    fun findPostListOrderByIdDesc(): List<PostListResponseDto>

    fun findPostById(id: Long): PostDetailResponseDto?

    fun save(postSaveDto: PostSaveDto, httpServletRequest: HttpServletRequest): PostDetailResponseDto?

    fun update(id: Long, postUpdateDto: PostUpdateDto, httpServletRequest: HttpServletRequest): PostDetailResponseDto?

    fun deleteById(id: Long, httpServletRequest: HttpServletRequest): String?

}