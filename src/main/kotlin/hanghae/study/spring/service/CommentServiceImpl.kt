package hanghae.study.spring.service

import hanghae.study.spring.api.dto.CommentDetailResponseDto
import hanghae.study.spring.api.dto.CommentSaveDto
import hanghae.study.spring.api.dto.CommentUpdateDto
import hanghae.study.spring.common.exception.MemberInvalidateException
import hanghae.study.spring.domain.Member
import hanghae.study.spring.domain.Role
import hanghae.study.spring.repository.CommentJpaRepository
import hanghae.study.spring.repository.PostJpaRepository
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
    private val postJpaRepository: PostJpaRepository
) : CommentService {

    override fun findCommentListByMember(member: Member) : CommentDetailResponseDto {
        val comment = commentJpaRepository.findByMember(member).orElseThrow()

        return CommentDetailResponseDto(
            id = comment.id, content = comment.content,
            postId = comment.post.id, memberId = comment.member.id,
            memberName = comment.member.name, createdAt = comment.createdAt)
    }

    @Transactional(readOnly = false)
    override fun deleteById(id: Long, member: Member) : String {
        val comment = commentJpaRepository.findByMember(member).orElseThrow()

        if(tokenMemberValidation(id, member)) {
            commentJpaRepository.delete(comment)
            return "success"
        }

        throw MemberInvalidateException("작성자 이외 사용자는 삭제할 수 없습니다.");
    }

    @Transactional(readOnly = false)
    override fun update(id : Long,
                        commentUpdateDto : CommentUpdateDto,
                        member : Member): CommentDetailResponseDto {
        val comment = commentJpaRepository.findById(id).orElseThrow()

        if(tokenMemberValidation(id, member)){
            comment.update(commentUpdateDto)

            return CommentDetailResponseDto(
                id = comment.id, content = comment.content,
                postId = comment.post.id, memberId = comment.member.id,
                memberName = comment.member.name, createdAt = comment.createdAt)
        }

        throw MemberInvalidateException("작성자 이외 사용자는 삭제할 수 없습니다.")
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

    private fun tokenMemberValidation(commentId: Long, member: Member) : Boolean {
        val commentMember = commentJpaRepository.findById(commentId)
            .orElseThrow().member

        if(member.role == Role.ADMIN){
            //Role.ADMIN의 경우 게시물 수정/삭제 허용
            log.info("ADMIN MEMBER REQUEST [COMMENT_ID: ${commentId}]")
            return true;
        }

        return commentMember == member
    }
}