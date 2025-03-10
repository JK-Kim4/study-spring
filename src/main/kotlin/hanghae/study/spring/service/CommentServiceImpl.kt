package hanghae.study.spring.service

import hanghae.study.spring.api.dto.CommentDetailResponseDto
import hanghae.study.spring.api.dto.CommentSaveDto
import hanghae.study.spring.api.dto.CommentUpdateDto
import hanghae.study.spring.common.exception.MemberInvalidateException
import hanghae.study.spring.common.jwt.JwtUtil
import hanghae.study.spring.domain.Member
import hanghae.study.spring.domain.Role
import hanghae.study.spring.repository.CommentJpaRepository
import hanghae.study.spring.repository.MemberJpaRepository
import hanghae.study.spring.repository.PostJpaRepository
import jakarta.servlet.http.HttpServletRequest
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.hibernate.query.sqm.tree.SqmNode.log
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
class CommentServiceImpl(
    private val commentJpaRepository: CommentJpaRepository,
    private val postJpaRepository: PostJpaRepository,
    private val memberJpaRepository: MemberJpaRepository,
    private val jwtUtil: JwtUtil
) : CommentService {

    override fun findCommentListByMember(member: Member) : CommentDetailResponseDto {
        val comment = commentJpaRepository.findByMember(member).orElseThrow()

        return CommentDetailResponseDto(
            id = comment.id, content = comment.content,
            postId = comment.post.id, memberId = comment.member.id,
            memberName = comment.member.name, createdAt = comment.createdAt)
    }

    @Transactional(readOnly = false)
    override fun deleteById(id: Long, httpServletRequest: HttpServletRequest) : String {
        if(tokenMemberValidation(id, httpServletRequest)) {
            commentJpaRepository.deleteById(id)
            return "success"
        }

        throw MemberInvalidateException();
    }

    @Transactional(readOnly = false)
    override fun update(id : Long,
                        commentUpdateDto : CommentUpdateDto,
                        httpServletRequest: HttpServletRequest): CommentDetailResponseDto {

        val comment = commentJpaRepository.findById(id).orElseThrow()

        if(tokenMemberValidation(id, httpServletRequest)){
            comment.update(commentUpdateDto)

            return CommentDetailResponseDto(
                id = comment.id, content = comment.content,
                postId = comment.post.id, memberId = comment.member.id,
                memberName = comment.member.name, createdAt = comment.createdAt)
        }

        throw MemberInvalidateException()
    }

    @Transactional(readOnly = false)
    override fun save(commentSaveDto: CommentSaveDto, member: Member): CommentDetailResponseDto {
        val post = postJpaRepository.findById(commentSaveDto.postId).orElseThrow()

        var comment = commentSaveDto.toComment(post = post, member = member)
        comment = commentJpaRepository.save(comment)

        return CommentDetailResponseDto(
            id = comment.id, content = comment.content,
            postId = comment.post.id, memberId = comment.member.id,
            memberName = comment.member.name, createdAt = comment.createdAt)
    }

    private fun tokenMemberValidation(commentId: Long, httpServletRequest: HttpServletRequest): Boolean {
        val member = commentJpaRepository.findById(commentId)
            .orElseThrow().member

        if(member.role == Role.ADMIN){
            //Role.ADMIN의 경우 게시물 수정/삭제 허용
            log.info("ADMIN MEMBER REQUEST [COMMENT_ID: ${commentId}]")
            return true;
        }

        val tokenMemberName = jwtUtil.getUserInfoFromToken(jwtUtil.resolveToken(httpServletRequest)).subject

        return member.name == tokenMemberName!!
    }
}