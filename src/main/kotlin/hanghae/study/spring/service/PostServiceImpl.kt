package hanghae.study.spring.service

import hanghae.study.spring.api.dto.PostDetailResponseDto
import hanghae.study.spring.api.dto.PostListResponseDto
import hanghae.study.spring.api.dto.PostSaveDto
import hanghae.study.spring.api.dto.PostUpdateDto
import hanghae.study.spring.common.exception.MemberInvalidateException
import hanghae.study.spring.common.jwt.JwtUtil
import hanghae.study.spring.domain.Member
import hanghae.study.spring.domain.Role
import hanghae.study.spring.repository.MemberJpaRepository
import hanghae.study.spring.repository.PostJpaRepository
import jakarta.servlet.http.HttpServletRequest
import lombok.RequiredArgsConstructor
import org.hibernate.query.sqm.tree.SqmNode.log
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
class PostServiceImpl(
    private val postJpaRepository: PostJpaRepository ,
    private val memberJpaRepository: MemberJpaRepository,
    private val jwtUtil: JwtUtil) : PostService {

    override fun findPostList(): List<PostListResponseDto> {
        val postList = postJpaRepository.findAll()

        return postList
            .map { PostListResponseDto(it.id, it.title, it.content, it.createdAt) }
            .toList()
    }

    override fun findPostListOrderByIdDesc(): List<PostListResponseDto> {
        val postList = postJpaRepository.findByOrderByIdDesc()

        return postList
            .map { PostListResponseDto(it.id, it.title, it.content, it.createdAt) }
            .toList()
    }

    override fun findPostById(id: Long): PostDetailResponseDto? {
        val post = postJpaRepository.findById(id).orElseThrow()
        return PostDetailResponseDto(post.id, post.title, post.content, post.member!!.id, post.createdAt)
    }

    @Transactional(readOnly = false)
    override fun save(postSaveDto: PostSaveDto,
                        httpServletRequest: HttpServletRequest): PostDetailResponseDto? {
        var post = postSaveDto.toPost();
        post.member = getMemberFromToken(httpServletRequest)
        post = postJpaRepository.save(post)

        return PostDetailResponseDto(post.id, post.title, post.content, post.member!!.id, post.createdAt)
    }

    @Transactional(readOnly = false)
    override fun update(id: Long, postUpdateDto: PostUpdateDto,
                        httpServletRequest: HttpServletRequest): PostDetailResponseDto? {
        if(tokenMemberValidation(id, httpServletRequest)) {

            // 2025.03.06 Token Member 인증 방식 변경
            // val post = postRepository.findByIdAndPassword(id, postUpdateDto.password).orElseThrow();
            val post = postJpaRepository.findById(id).orElseThrow()
            post.update(postUpdateDto)
            return PostDetailResponseDto(post.id, post.title, post.content, post.member!!.id, post.createdAt)
        }

        throw MemberInvalidateException();

    }

    @Transactional(readOnly = false)
    override fun deleteById(id: Long,
                            httpServletRequest: HttpServletRequest): String?{

        if(tokenMemberValidation(id, httpServletRequest)) {
            postJpaRepository.deleteById(id)
            return "success"
        }

        throw MemberInvalidateException();
    }

    private fun tokenMemberValidation(postId: Long, httpServletRequest: HttpServletRequest): Boolean {
        val member = postJpaRepository.findById(postId)
            .orElseThrow().member

        if(member!!.role == Role.ADMIN){
            //ADMIN의 경우 게시물 수정/삭제 허용
            log.info("ADMIN MEMBER REQUEST [POST-ID] : ${postId}]")
            return true
        }

        val tokenMemberName = jwtUtil.getUserInfoFromToken(jwtUtil.resolveToken(httpServletRequest)).subject

        return member.name == tokenMemberName!!
    }

    private fun getMemberFromToken(httpServletRequest: HttpServletRequest): Member {
        val tokenMemberName = jwtUtil.getUserInfoFromToken(jwtUtil.resolveToken(httpServletRequest)).subject

        return memberJpaRepository.findByName(tokenMemberName).orElseThrow()
    }
}