package hanghae.study.spring.service

import hanghae.study.spring.api.dto.CommentDetailResponseDto
import hanghae.study.spring.api.dto.CommentSaveDto
import hanghae.study.spring.api.dto.CommentUpdateDto
import hanghae.study.spring.domain.Member
import jakarta.servlet.http.HttpServletRequest

interface CommentService {

    fun save(commentSaveDto: CommentSaveDto, member : Member) : CommentDetailResponseDto

    fun update(id : Long, commentUpdateDto: CommentUpdateDto, member : Member) : CommentDetailResponseDto

    fun deleteById(id: Long, member : Member) : String

    fun findCommentListByMember(member : Member) : CommentDetailResponseDto

}