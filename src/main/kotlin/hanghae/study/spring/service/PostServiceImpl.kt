package hanghae.study.spring.service

import hanghae.study.spring.api.dto.*
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
            .map { PostListResponseDto(
                id = it.id, title = it.title,
                content = it.content, commentCount = it.comments?.size,
                createdAt = it.createdAt) }
            .toList()
    }

    override fun findPostListOrderByIdDesc(): List<PostListResponseDto> {
        val postList = postJpaRepository.findByOrderByIdDesc()

        return postList
            .map { PostListResponseDto(
                id = it.id, title = it.title,
                content = it.content, commentCount = it.comments?.size,
                createdAt = it.createdAt) }
            .toList()
    }

    override fun findPostById(id: Long): PostDetailResponseDto {
        val post = postJpaRepository.findById(id).orElseThrow()
        return PostDetailResponseDto(
            id = post.id!!, title = post.title,
            content = post.content, memberId = post.member!!.id,
            comments = post.comments
                ?.map { comment -> CommentListResponseDto(
                    commentId = comment.id, postId = comment.post.id,
                    memberId = comment.member.id, memberName = comment.member.name,
                    content = comment.content) }?.toList() ?: mutableListOf(),
            createdAt = post.createdAt)
    }

    @Transactional(readOnly = false)
    override fun save(postSaveDto: PostSaveDto, member: Member): PostDetailResponseDto {
        var post = postSaveDto.toPost();
        post.member = member

        post = postJpaRepository.save(post)

        return PostDetailResponseDto(
            id = post.id!!, title = post.title,
            content = post.content, memberId = post.member!!.id,
            comments = post.comments
                ?.map { comment -> CommentListResponseDto(
                    commentId = comment.id, postId = comment.post.id,
                    memberId = comment.member.id, memberName = comment.member.name,
                    content = comment.content) }?.toList() ?: mutableListOf(),
            createdAt = post.createdAt)
    }

    @Transactional(readOnly = false)
    override fun update(
        postId: Long, postUpdateDto: PostUpdateDto,
        member: Member): PostDetailResponseDto {
        if(tokenMemberValidation(postId, member)) {

            // 2025.03.06 Token Member 인증 방식 변경
            // val post = postRepository.findByIdAndPassword(id, postUpdateDto.password).orElseThrow();
            val post = postJpaRepository.findById(postId).orElseThrow()
            post.update(postUpdateDto)
            return PostDetailResponseDto(
                id = post.id!!, title = post.title,
                content = post.content, memberId = post.member!!.id,
                comments = post.comments
                    ?.map { comment -> CommentListResponseDto(
                        commentId = comment.id, postId = comment.post.id,
                        memberId = comment.member.id, memberName = comment.member.name,
                        content = comment.content) }?.toList() ?: mutableListOf(),
                createdAt = post.createdAt)
        }

        throw MemberInvalidateException()

    }

    @Transactional(readOnly = false)
    override fun deleteById(
        id: Long, member: Member): String{

        if(tokenMemberValidation(id, member)) {
            postJpaRepository.deleteById(id)
            return "success"
        }

        throw MemberInvalidateException();
    }

    private fun tokenMemberValidation(postId: Long, member: Member): Boolean {
        val postAuthor = postJpaRepository.findById(postId)
            .orElseThrow().member

        if(postAuthor!!.role == Role.ADMIN){
            //ADMIN의 경우 게시물 수정/삭제 허용
            log.info("ADMIN MEMBER REQUEST [POST-ID] : ${postId}]")
            return true
        }

        return postAuthor == member
    }

    private fun getMemberFromToken(httpServletRequest: HttpServletRequest): Member {
        val tokenMemberName = jwtUtil.getUserInfoFromToken(jwtUtil.resolveToken(httpServletRequest)).subject

        return memberJpaRepository.findByName(tokenMemberName).orElseThrow()
    }
}