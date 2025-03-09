package hanghae.study.spring.service

import hanghae.study.spring.api.dto.PostDetailResponseDto
import hanghae.study.spring.api.dto.PostListResponseDto
import hanghae.study.spring.api.dto.PostSaveDto
import hanghae.study.spring.api.dto.PostUpdateDto
import hanghae.study.spring.common.exception.MemberInvalidateException
import hanghae.study.spring.common.jwt.JwtUtil
import hanghae.study.spring.domain.Member
import hanghae.study.spring.repository.MemberJpaRepository
import hanghae.study.spring.repository.PostJpaRepository
import jakarta.servlet.http.HttpServletRequest
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
@RequiredArgsConstructor
class PostServiceImpl(
    private val postRepository: PostJpaRepository,
    private val memberJpaRepository: MemberJpaRepository,
    private val jwtUtil: JwtUtil) : PostService {

    override fun findPostList(): List<PostListResponseDto> {
        val postList = postRepository.findAll()

        return postList
            .map { PostListResponseDto(it.id, it.title, it.content, it.createdAt) }
            .toList()
    }

    override fun findPostListOrderByIdDesc(): List<PostListResponseDto> {
        val postList = postRepository.findByOrderByIdDesc()

        return postList
            .map { PostListResponseDto(it.id, it.title, it.content, it.createdAt) }
            .toList()
    }

    override fun findPostById(id: Long): PostDetailResponseDto? {
        val post = postRepository.findById(id).orElseThrow()
        return PostDetailResponseDto(post.id, post.title, post.content, post.member!!.id, post.createdAt)
    }

    override fun save(postSaveDto: PostSaveDto,
                        httpServletRequest: HttpServletRequest): PostDetailResponseDto? {
        var post = postSaveDto.toPost();
        post.member = getMemberFromToken(httpServletRequest)
        post = postRepository.save(post)

        return PostDetailResponseDto(post.id, post.title, post.content, post.member!!.id, post.createdAt)
    }

    override fun update(id: Long, postUpdateDto: PostUpdateDto,
                        httpServletRequest: HttpServletRequest): PostDetailResponseDto? {
        if(tokenMemberValidation(id, httpServletRequest)) {

            // 2025.03.06 Token Member 인증 방식 변경
            // val post = postRepository.findByIdAndPassword(id, postUpdateDto.password).orElseThrow();
            val post = postRepository.findById(id).orElseThrow()
            post.update(postUpdateDto)
            return PostDetailResponseDto(post.id, post.title, post.content, post.member!!.id, post.createdAt)
        }

        throw MemberInvalidateException();

    }

    override fun deleteById(id: Long,
                            httpServletRequest: HttpServletRequest): String?{

        if(tokenMemberValidation(id, httpServletRequest)) {
            postRepository.deleteById(id)
            return "success"
        }

        throw MemberInvalidateException();
    }

    private fun tokenMemberValidation(postId: Long, httpServletRequest: HttpServletRequest): Boolean {
        val member = postRepository.findById(postId)
            .orElseThrow().member

        val tokenMemberName = jwtUtil.getUserInfoFromToken(jwtUtil.resolveToken(httpServletRequest)).subject

        return member!!.name == tokenMemberName!!
    }

    private fun getMemberFromToken(httpServletRequest: HttpServletRequest): Member {
        val tokenMemberName = jwtUtil.getUserInfoFromToken(jwtUtil.resolveToken(httpServletRequest)).subject

        return memberJpaRepository.findByName(tokenMemberName).orElseThrow()
    }
}